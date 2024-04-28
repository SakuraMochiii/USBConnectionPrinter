package com.wizarpos.usbconnectionprinter;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbRequest;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

public class PrinterUtil {
    private static UsbManager usbManager;
    /**
     * 满足的设备
     */
    private static UsbDevice myUsbDevice;
    /**
     * usb接口
     */
    private static UsbInterface usbInterface;
    /**
     * 块输出端点
     */
    private static UsbEndpoint epBulkOut;
    private static UsbEndpoint epBulkIn;
    /**
     * 控制端点
     */
    private static UsbEndpoint epControl;
    /**
     * 中断端点
     */
    private static UsbEndpoint epIntEndpointOut;
    private static UsbEndpoint epIntEndpointIn;
    /**
     * 连接
     */
    private static UsbDeviceConnection myDeviceConnection;

    /**
     * 初始化打印机(获取usb打印设备,连接打印机,获取USB连接权限)
     *
     * @param activity
     */
    public static void init(Activity activity) {
        // 1)创建usbManager
        usbManager = (UsbManager) activity
                .getSystemService(Context.USB_SERVICE);
        enumeraterDevices(activity);
        getDeviceInterface();
        assignEndpoint();
    }

    /**
     * 枚举设备
     */
    private static void enumeraterDevices(Activity activity) {
        HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
        while (deviceIterator.hasNext()) {
            UsbDevice device = deviceIterator.next();
            Log.i("PrinterUtil", "SQLTestgetDeviceInterface device.getVendorId() : " + device.getVendorId() + ";" + device.getProductId());
            // 每个打印机的vendorId和productId都不同,需要自行替换
            if (device.getVendorId() == 1208 && device.getProductId() == 514) {
                myUsbDevice = device; // 获取USBDevice
                PendingIntent pi = PendingIntent.getBroadcast(activity, 0,
                        new Intent(ACTION_USB_PERMISSION), 0);
                usbManager.requestPermission(myUsbDevice, pi);
            }
        }
    }

    /**
     * 获取设备的接口
     */
    private static void getDeviceInterface() {
        Log.i("PrinterUtil", "SQLTestgetDeviceInterface interfaceCounts : " + myUsbDevice);
        if (myUsbDevice != null) {
            Log.i("PrinterUtil", "interfaceCounts : " + myUsbDevice.getInterfaceCount());
            usbInterface = myUsbDevice.getInterface(0);
            Log.i("PrinterUtil", "成功获得设备接口:" + usbInterface.getId());
        }
    }

    /**
     * 分配端点，IN | OUT，即输入输出；可以通过判断
     */
    private static void assignEndpoint() {
        if (usbInterface != null) {
            for (int i = 0; i < usbInterface.getEndpointCount(); i++) {
                UsbEndpoint ep = usbInterface.getEndpoint(i);
                switch (ep.getType()) {
                    case UsbConstants.USB_ENDPOINT_XFER_BULK:// 块
                        if (UsbConstants.USB_DIR_OUT == ep.getDirection()) {// 输出
                            epBulkOut = ep;
                            Log.i("PrinterUtil", "Find the BulkEndpointOut,"
                                    + "index:" + i + "," + "使用端点号："
                                    + epBulkOut.getEndpointNumber());
                        } else {
                            epBulkIn = ep;
                            Log.i("PrinterUtil", "Find the BulkEndpointIn:"
                                    + "index:" + i + "," + "使用端点号："
                                    + epBulkIn.getEndpointNumber());
                        }
                        break;
                    case UsbConstants.USB_ENDPOINT_XFER_CONTROL:// 控制
                        epControl = ep;
                        Log.i("PrinterUtil", "find the ControlEndPoint:" + "index:"
                                + i + "," + epControl.getEndpointNumber());
                        break;
                    case UsbConstants.USB_ENDPOINT_XFER_INT:// 中断
                        if (ep.getDirection() == UsbConstants.USB_DIR_OUT) {// 输出
                            epIntEndpointOut = ep;
                            Log.i("PrinterUtil", "find the InterruptEndpointOut:"
                                    + "index:" + i + ","
                                    + epIntEndpointOut.getEndpointNumber());
                        }
                        if (ep.getDirection() == UsbConstants.USB_DIR_IN) {
                            epIntEndpointIn = ep;
                            Log.i("PrinterUtil", "find the InterruptEndpointIn:"
                                    + "index:" + i + ","
                                    + epIntEndpointIn.getEndpointNumber());
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";

    /**
     * 连接设备
     */
    public static void openDevice() {
        Log.i("PrinterUtil", "SQLTestprintText openDevice111 : " + usbInterface);
        if (usbInterface != null) {// 接口是否为null
            // 在open前判断是否有连接权限；对于连接权限可以静态分配，也可以动态分配权限
            UsbDeviceConnection conn = null;
            Log.i("PrinterUtil", "SQLTestprintText openDevice222 : " + usbManager.hasPermission(myUsbDevice));
            if (usbManager.hasPermission(myUsbDevice)) {
                // 有权限，那么打开
                conn = usbManager.openDevice(myUsbDevice);
            }
            if (null == conn) {
                Log.i("PrinterUtil", "不能连接到设备");
                return;
            }
            // 打开设备
            if (conn.claimInterface(usbInterface, true)) {
                myDeviceConnection = conn;
                if (myDeviceConnection != null)// 到此你的android设备已经连上zigbee设备
                    Log.i("PrinterUtil", "open设备成功！");
                final String mySerial = myDeviceConnection.getSerial();
                Log.i("PrinterUtil", "设备serial number：" + mySerial);
            } else {
                Log.i("PrinterUtil", "无法打开连接通道。");
                conn.close();
            }
        }
    }

    public static void queryStatus(Context context) {

        try {
            UsbRequest request = new UsbRequest();
            request.initialize(myDeviceConnection, epBulkIn);

//			ByteBuffer buffer = ByteBuffer.allocate(1);
            int inMax = epBulkIn.getMaxPacketSize();
            ByteBuffer byteBuffer = ByteBuffer.allocate(inMax);

            // queue a request on the interrupt endpoint
            request.queue(byteBuffer, inMax);

            /*
             * int requestType：确定数据流的方向、请求类型以及接收端；由于是与HID通信，所以它只能是0 01 00001 (0x21)和1010 0001(0xA1)，再把它转为16进制填上去就可以了。
             * 比特位7是指数据传输的方向：从主机到设备（OUT）为0，从设备到主机（IN）为1；比特位6到5是指请求类型：USB标准请求为00，USB类请求为01，
             * 厂商的自定义请求为10；比特位4到0是指接收端：指向设备（00000）、专属接口（00001）、端点（00010）、设备中的其它元件（00011）。
             * int request：这个参数是对应的请求号。在我的demo中，使用到的请求是Set Report（因为在我的demo中需要发送报表命令请求数据，所以才用Set Report；不同情况要不同请求）,其对应的值就是0x09（查一下就知道了）了。
             * int value：可将请求传递给设备，有两个字节。对应于Set Preport，高字节是报告类型（02为输出，03为特征），低字节是报告ID（默认为0）。
             * int index：可将请求传递给设备，有两个字节。典型的应用是传递索引或者诸如接口或端点号的偏移量（这些需要查询自己的设备信息了，我这里的HID的接口索引值为0）。
             * 原文链接：https://blog.csdn.net/gd6321374/article/details/78014255
             */
            // send poll status command
            byte[] buffer = new byte[]{29, 73, 67};
            myDeviceConnection.controlTransfer(0xA1, 2, 0, 0, buffer, buffer.length, 10000);
            // wait for status event
            if (myDeviceConnection.requestWait() == request) {
                byte[] retData = byteBuffer.array();
                StringBuilder bts = new StringBuilder();
                for (int i = 0; i < retData.length; i++) {
                    bts.append(retData[i]).append("  ");
                }
                Log.i("PrinterUtil", "SQLTestprint queryStatus: The printer state is " + bts);
                Toast.makeText(context, "The printer status : " + bts + " .", Toast.LENGTH_LONG).show();
            } else {
                Log.e("PrinterUtil", "requestWait failed, exiting");
            }
            myDeviceConnection.releaseInterface(usbInterface);
            myDeviceConnection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void printText(Context context, final String txt) {

        byte[] bys1 = PrinterCommand1.getCmdBold(1);
        myDeviceConnection.bulkTransfer(epBulkOut, bys1, bys1.length, 3000);

        byte[] bys4 = PrinterCommand1.getCmdReverse(1);
        myDeviceConnection.bulkTransfer(epBulkOut, bys4, bys4.length, 3000);

        byte[] buffer = txt.getBytes(StandardCharsets.UTF_8);
        int printed = myDeviceConnection.bulkTransfer(epBulkOut, buffer, buffer.length, 3000);


        byte[] bys3 = PrinterCommand1.getCmdReverse(0);
        myDeviceConnection.bulkTransfer(epBulkOut, bys3, bys3.length, 3000);
        byte[] bys2 = PrinterCommand1.getCmdBold(0);
        myDeviceConnection.bulkTransfer(epBulkOut, bys2, bys2.length, 3000);

        printed = myDeviceConnection.bulkTransfer(epBulkOut, buffer, buffer.length, 3000);


        byte[] bys5 = PrinterCommand1.getCmdEscJN(200);
        myDeviceConnection.bulkTransfer(epBulkOut, bys5, bys5.length, 3000);
        myDeviceConnection.bulkTransfer(epBulkOut, bys5, bys5.length, 3000);


        Log.i("PrinterUtil", "SQLTestprint  Text: The printer has printed " + printed + " chars.");
        if (printed > -1) {
            Toast.makeText(context, "The printer has printed " + printed + " chars.", Toast.LENGTH_LONG).show();
        }

    }

    public static void getProductInfo() {
        /*
        29,73,n
        n:
        66    * Manufacturer  “EPSON”
        67 Printer name  “TM-T88IV”
        68 Product ID
        */
        byte[] data1 = {29, 73, 66, 29, 73, 67, 29, 73, 68};//Product name
        int a = myDeviceConnection.bulkTransfer(epBulkOut, data1, data1.length, 1000);
        byte[] data2 = new byte[32];
        int b = myDeviceConnection.bulkTransfer(epBulkIn, data2, data2.length, 1000);
        StringBuilder bts = new StringBuilder();
        for (int i = 0; i < b; i++) {
            bts.append(data2[i]).append("  ");
        }
        //decimal to ascii
        StringBuilder sbu = new StringBuilder();
        String[] chars = bts.toString().split("  ");
        for (String aChar : chars) {
            sbu.append((char) Integer.parseInt(aChar));
        }
        Log.i("PrinterUtil", "SQLTestprint getProductInfo: data length:" + b);
        Log.i("PrinterUtil", "SQLTestprint getProductInfo: The Printer Device info:" + sbu);
        myDeviceConnection.releaseInterface(usbInterface);
        myDeviceConnection.close();
    }
}
