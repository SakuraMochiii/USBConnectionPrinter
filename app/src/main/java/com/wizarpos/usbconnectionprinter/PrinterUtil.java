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
     * Valid Devices
     */
    private static UsbDevice myUsbDevice;
    /**
     * USB Interface
     */
    private static UsbInterface usbInterface;
    /**
     * Bulk Output Endpoint
     */
    private static UsbEndpoint epBulkOut;
    private static UsbEndpoint epBulkIn;
    /**
     * Control Endpoint
     */
    private static UsbEndpoint epControl;
    /**
     * Interrupt Endpoint
     */
    private static UsbEndpoint epIntEndpointOut;
    private static UsbEndpoint epIntEndpointIn;
    /**
     * Connection
     */
    private static UsbDeviceConnection myDeviceConnection;

    /**
     * Initialize printer (get USB printing device, connect printer, obtain USB connection permission)
     *
     * @param activity
     */
    public static void init(Activity activity) {
        // 1) Create USBManager
        usbManager = (UsbManager) activity
                .getSystemService(Context.USB_SERVICE);
        enumeraterDevices(activity);
        getDeviceInterface();
        assignEndpoint();
    }

    /**
     * Enumerate devices
     */
    private static void enumeraterDevices(Activity activity) {
        HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
        while (deviceIterator.hasNext()) {
            UsbDevice device = deviceIterator.next();
            Log.i("PrinterUtil", "SQLTestgetDeviceInterface device.getVendorId() : " + device.getVendorId() + ";" + device.getProductId());
            // Each printer has a different vendor ID and product ID, which need to be replaced accordingly.
            if (device.getVendorId() == 1208 && device.getProductId() == 514) {
                myUsbDevice = device; // Get USBDevice
                PendingIntent pi = PendingIntent.getBroadcast(activity, 0,
                        new Intent(ACTION_USB_PERMISSION), 0);
                usbManager.requestPermission(myUsbDevice, pi);
            }
        }
    }

    /**
     * Get device interfaces
     */
    private static void getDeviceInterface() {
        Log.i("PrinterUtil", "SQLTestgetDeviceInterface interfaceCounts : " + myUsbDevice);
        if (myUsbDevice != null) {
            Log.i("PrinterUtil", "interfaceCounts : " + myUsbDevice.getInterfaceCount());
            usbInterface = myUsbDevice.getInterface(0);
            Log.i("PrinterUtil", "Successfully obtained device interface: " + usbInterface.getId());
        }
    }

    /**
     * Assign endpoints, IN | OUT, for input and output; can be determined through evaluation.
     */
    private static void assignEndpoint() {
        if (usbInterface != null) {
            for (int i = 0; i < usbInterface.getEndpointCount(); i++) {
                UsbEndpoint ep = usbInterface.getEndpoint(i);
                switch (ep.getType()) {
                    case UsbConstants.USB_ENDPOINT_XFER_BULK:// bulk
                        if (UsbConstants.USB_DIR_OUT == ep.getDirection()) {// Output
                            epBulkOut = ep;
                            Log.i("PrinterUtil", "Find the BulkEndpointOut,"
                                    + "index:" + i + "," + "Using endpoint number: "
                                    + epBulkOut.getEndpointNumber());
                        } else {
                            epBulkIn = ep;
                            Log.i("PrinterUtil", "Find the BulkEndpointIn:"
                                    + "index:" + i + "," + "Using endpoint number: "
                                    + epBulkIn.getEndpointNumber());
                        }
                        break;
                    case UsbConstants.USB_ENDPOINT_XFER_CONTROL:// Control
                        epControl = ep;
                        Log.i("PrinterUtil", "find the ControlEndPoint:" + "index:"
                                + i + "," + epControl.getEndpointNumber());
                        break;
                    case UsbConstants.USB_ENDPOINT_XFER_INT:// Interrupt
                        if (ep.getDirection() == UsbConstants.USB_DIR_OUT) {// Output
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
     * Connect device
     */
    public static void openDevice() {
        Log.i("PrinterUtil", "SQLTestprintText openDevice111 : " + usbInterface);
        if (usbInterface != null) {// Whether the interface is null
            // Check if there is permission to connect before opening; permissions for connection can be statically or dynamically allocated.
            UsbDeviceConnection conn = null;
            Log.i("PrinterUtil", "SQLTestprintText openDevice222 : " + usbManager.hasPermission(myUsbDevice));
            if (usbManager.hasPermission(myUsbDevice)) {
                // If there is permission, then open.
                conn = usbManager.openDevice(myUsbDevice);
            }
            if (null == conn) {
                Log.i("PrinterUtil", "Unable to connect to the device.");
                return;
            }
            // Open the device.
            if (conn.claimInterface(usbInterface, true)) {
                myDeviceConnection = conn;
                if (myDeviceConnection != null)// Your Android device is now connected to the Zigbee device.
                    Log.i("PrinterUtil", "Open device successful");
                final String mySerial = myDeviceConnection.getSerial();
                Log.i("PrinterUtil", "Device serial number：" + mySerial);
            } else {
                Log.i("PrinterUtil", "Unable to open connection channel.");
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
             * int requestType：Determine the direction of data flow, request type, and recipient. 
             *     Since it's HID communication, it can only be 0 01 00001 (0x21) and 1010 0001 (0xA1). 
             *     Convert these values to hexadecimal and fill accordingly.
             *         Bit 7 indicates the direction of data transmission: 0 for host to device (OUT), 1 for device to host (IN).
             *         Bits 6 to 5 indicate the request type: 00 for USB standard request, 01 for USB class request, 10 for vendor-defined request.
             *         Bits 4 to 0 indicate the recipient: directed to device (00000), to specific interface (00001), to endpoint (00010), to other components in the device (00011).
             * int request：This parameter corresponds to the request number. In this demo, the request used is Set Report (to send a report command to request data); Its corresponding value is 0x09.
             * int value：The request is passed to the device using two bytes. For Set Report, the first byte represents the report type (02 for output, 03 for feature), and the last byte represents the report ID (default is 0).
             * int index：The request is passed to the device using two bytes. A typical application is to pass an index or an offset such as interface or endpoint numbers (this information needs to be retrieved from your device; in this case, the HID interface index value is 0).
             * Original link：https://blog.csdn.net/gd6321374/article/details/78014255
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
