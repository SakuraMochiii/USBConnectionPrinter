
package com.wizarpos.usbconnectionprinter;

public class PrinterCommand1
{
    /*--------------------------打印命令-----------------------------*/

    /**
     * 打印行缓冲器里的内容并向前走纸一行。当行缓冲器为空时只向前走纸一行。
     * 
     * @return
     */
    static public byte[] getCmdLf()
    {
        return new byte[] {
                (byte) 0x0A
        };
    }

    /**
     * 打印位置跳到下一个制表位,制表位为 8 个字符的起始位置
     * 
     * @return
     */
    static public byte[] getCmdHt()
    {
        return new byte[] {
                (byte) 0x09
        };
    }

    /**
     * 打印缓冲区里的数据,如果有黑标功能,打印后进纸到下一个黑标位置
     * 
     * @return
     */
    static public byte[] getCmdFf()
    {
        return new byte[] {
                (byte) 0x0c
        };
    }

    /**
     * 打印行缓冲区里的内容,并向前走纸 n 点行。 该命令只对本行有效,不改变 ESC 2,ESC 3 命令设置的行间距值。
     * 
     * @param n 0-255
     * @return
     */
    static public byte[] getCmdEscJN(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x4A, (byte) n
        };
    }

    /**
     * 打印缓冲区里的数据,如果有黑标功能,打印后进纸到下一个黑标位置
     * 
     * @return
     */
    static public byte[] getCmdEscFf()
    {
        return new byte[] {
                (byte) 0x1b, (byte) 0x0c
        };
    }

    /**
     * 打印行缓冲区里的内容,并向前走纸 n 行。 行高为 ESC 2,ESC 3 设定的值
     * 
     * @param n 0-255
     * @return
     */
    static public byte[] getCmdEscDN(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x64, (byte) n
        };
    }

    /**
     * 设置中文小字体
     */
    static public byte[] getCmdSetSmallFont_CN()
    {
        return new byte[] {
                (byte) 0x1C, (byte) 0x21, (byte) 0x01
        };
    }

    /**
     * 取消中文小字体
     */
    static public byte[] getCmdCancelSmallFont_CN()
    {
        return new byte[] {
                (byte) 0x1C, (byte) 0x21, (byte) 0x00
        };
    }

    /**
     * 设置英文小字体
     */
    static public byte[] getCmdSetSmallFont_EN()
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x21, (byte) 0x01
        };
    }

    /**
     * 取消英文小字体
     */
    static public byte[] getCmdCancelSmallFont_EN()
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x21, (byte) 0x00
        };
    }

    /**
     * 1:打印机处于连线模式,接受打印数据并打印 0:打印机处于离线模式,不接受打印数据
     * 
     * @param n :0,1最低位有效
     * @return
     */
    static public byte[] getCmdEscN(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x3d, (byte) n
        };
    }

    /*--------------------------行间距设置命令-----------------------------*/

    /**
     * 设置行间距为 4 毫米,32 点
     * 
     * @return
     */
    static public byte[] getCmdEsc2()
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x32
        };
    }

    /**
     * 设置行间距为 n 点行。 默认值行间距是 32 点。
     * 
     * @param n :0-255
     * @return
     */
    static public byte[] getCmdEsc3N(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x33, (byte) n
        };
    }

    /**
     * 设置打印行的对齐方式,缺省:左对齐 0 ≤ n ≤ 2 或 48 ≤ n ≤ 50 左对齐: n=0,48 居中对齐: n=1,49 右对齐:
     * n=2,50
     * 
     * @param n :0 ≤ n ≤ 2 或 48 ≤ n ≤ 50
     * @return
     */
    static public byte[] getCmdEscAN(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x61, (byte) n
        };
    }

    /**
     * 设置打印的左边距,缺省为 0。左边距为 nL+nH*256,单位 0.125mm
     * 
     * @param nL
     * @param nH
     * @return
     */
    static public byte[] getCmdGsLNlNh(int nL, int nH)
    {
        return new byte[] {
                (byte) 0x1D, (byte) 0x4c, (byte) nL, (byte) nH
        };
    }

    /**
     * 设置打印的左边距,缺省为 0 左边距为 nL+nH*256,单位 0.125mm
     * 
     * @param nL
     * @param nH
     * @return
     */
    static public byte[] getCmdEsc$NlNh(int nL, int nH)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x24, (byte) nL, (byte) nH
        };
    }

    /*--------------------------字符设置命令-----------------------------*/

    /**
     * 用于设置打印字符的方式。默认值是 0
     * 
     * @param n 位 0:保留 位 1:字体反白 位 2:1:字体上下倒置 位 3:1:字体加粗 位 4:1:双倍高度 位 5:1:双倍宽度 位
     *            6:1:删除线
     * @return
     */
    static public byte[] getCmdEsc_N(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x21, (byte) n
        };
    }

    /**
     * n 的低 4 位表示高度是否放大,等于 0 表示不放大 n 的高 4 位表示宽度是否放大,等于 0 表示不放大
     * 
     * @param n
     * @return
     */
    static public byte[] getCmdGs_N(int n)
    {
        return new byte[] {
                (byte) 0x1D, (byte) 0x21, (byte) n
        };
    }

    /**
     * 等于 0 时取消字体加粗 非 0 时设置字体加粗
     * 
     * @param n 最低位有效
     * @return
     */
    static public byte[] getCmdEscEN(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x45, (byte) n
        };
    }

    /**
     * 默认值:0
     * 
     * @param n :表示两个字符之间的间距
     * @return
     */
    static public byte[] getCmdEscSpN(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x20, (byte) n
        };
    }

    /**
     * 该命令之后所有字符均以正常宽度的 2 倍打印; 该命令可以用回车或者 DC4 命令删除。
     * 
     * @return
     */
    static public byte[] getCmdEscSo()
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x0E
        };
    }

    /**
     * 命令执行后,字符恢复正常宽度打印
     * 
     * @return
     */
    static public byte[] getCmdEscDc4()
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x14
        };
    }

    /**
     * 默认:0
     * 
     * @param n n=1:设置字符上下倒置 n=0:取消字符上下倒置
     * @return
     */
    static public byte[] getCmdEsc__N(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x7B, (byte) n
        };
    }

    /**
     * 默认:0
     * 
     * @param n n=1:设置字符反白打印 n=0:取消字符反白打印
     * @return
     */
    static public byte[] getCmdGsBN(int n)
    {
        return new byte[] {
                (byte) 0x1D, (byte) 0x42, (byte) n
        };
    }

    /**
     * 默认:0
     * 
     * @param n n=0-2,下划线的高度
     * @return
     */
    static public byte[] getCmdEsc___N(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x2D, (byte) n
        };
    }

    /**
     * @param n n=1:选择用户自定义字符集; n=0:选择内部字符集(默认)
     * @return
     */
    static public byte[] getCmdEsc____N(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x25, (byte) n
        };
    }

    /**
     * 用于设置用户自定义字符,最多可设置 32 个用户自定义字符。
     * 
     * @return
     */
    static public byte[] getCmdEsc_SNMW()
    {
        // 占位
        return null;
    }

    /**
     * 命令用于取消用户自定义的字符,字符取消后,使用系统的字符。
     * 
     * @param n
     * @return
     */
    static public byte[] getCmdEsc_____N(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x25, (byte) n
        };
    }

    /**
     * 选择国际字符集。中文版本不支持该命令。
     * 
     * @param n 国际字符集设置如下:0:USA 1:France 2:Germany 3:U.K. 4:Denmark 1 5:Sweden
     *            6:Italy 7:Spain1 8:Japan 9:Norway 10:Denmark II 11:Spain II
     *            12:Latin America 13:Korea
     * @return
     */
    static public byte[] getCmdEscRN(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x52, (byte) n
        };
    }

    /**
     * 选择字符代码页,字符代码页用于选择 0x80~0xfe 的打印字符。中文版本不支持该命令
     * 
     * @param n 字符代码页参数如 下:0:437 1:850
     * @return
     */
    static public byte[] getCmdEscTN(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x74, (byte) n
        };
    }

    /*--------------------------图形打印命令 略 -----------------------------*/

    /*--------------------------按键控制命令-----------------------------*/

    /**
     * 允许/禁止按键开关命令,暂时不支持该命令。
     * 
     * @param n n=1,禁止按键 n=0,允许按键(默认)
     * @return
     */
    static public byte[] getCmdEscC5N(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x63, (byte) 0x35, (byte) n
        };
    }

    /*--------------------------初始化命令-----------------------------*/

    /**
     * 初始化打印机。清除打印缓冲区 恢复默认值 选择字符打印方式 删除用户自定义字符
     * 
     * @return
     */
    static public byte[] getCmdEsc_()
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x40
        };
    }

    /*--------------------------状态传输命令-----------------------------*/

    /**
     * 向主机传送控制板状态
     * 
     * @param n
     * @return
     */
    static public byte[] getCmdEscVN(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x76, (byte) n
        };
    }

    /**
     * 当有效时,打印机发现状态改变,则自动发送状态到主机。详细参照ESC/POS指令级。
     * 
     * @param n
     * @return
     */
    static public byte[] getCmdGsAN(int n)
    {
        return new byte[] {
                (byte) 1D, (byte) 61, (byte) n
        };
    }

    /**
     * 向主机传送周边设备状态,仅对串口型打印机有效。该命令不支持。详细参照ESC/POS指令集。
     * 
     * @param n
     * @return
     */
    static public byte[] getCmdEscUN(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x75, (byte) n
        };
    }

    /*--------------------------条码打印命令 略 -----------------------------*/

    /*--------------------------控制板参数命令 略 -----------------------------*/

    /**
     * 自定义制表位(2个空格)
     * 
     * @return
     */
    static public byte[] getCustomTabs()
    {
        return "  ".getBytes();
    }

    /**
     * 切纸命令
     */
    static public byte[] getCmdCutPaper()
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x69
        };
    }

    /**
     * 默认0
     * 
     * @param n 加粗 = 1/取消加粗 = 0
     */
    static public byte[] getCmdBold(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x45, (byte) n
        };
    }

    /**
     * <p>
     * 默认0
     * </p>
     * 
     * @param n 反白 = 1/取消反白 = 0
     */
    static public byte[] getCmdReverse(int n)
    {
        return new byte[] {
                (byte) 0x1D, (byte) 0x42, (byte) n
        };
    }

    /**
     * 默认:0
     * 
     * @param n 上下倒置 = 1/取消上下倒置 = 0
     */
    static public byte[] getCmdInversion(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x7B, (byte) n
        };
    }

    /**
     * 默认0
     * 
     * @param n 设置中文小字体 = 1/取消中文小字体 = 0
     */
    static public byte[] getCmdSmallFontCN(int n)
    {
        return new byte[] {
                (byte) 0x1C, (byte) 0x21, (byte) n
        };
    }

    /**
     * 默认0
     * 
     * @param n 设置英文小字体 = 1/取消英文小字体 = 0
     */
    static public byte[] getCmdSmallFontEN(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x21, (byte) n
        };
    }

    /**
     * 设置字体倍宽倍高
     * 
     * @param n = 0xAB
     *            <p>
     *            A 表示宽度的倍数, 0<A<7, 分别表示1-8倍宽度
     *            </p>
     *            <p>
     *            B 表示高度的倍数, 0<B<7, 分别表示1-8倍高度
     *            </p>
     */
    static public byte[] getCmdFontSize(int n)
    {
        return new byte[] {
                (byte) 0x1D, (byte) 0x21, (byte) n
        };
    }

    /**
     * 对齐方式
     * 
     * @param n <p>
     *            n = 0, 左对齐
     *            </p>
     *            <p>
     *            n = 1, 居中对齐
     *            </p>
     *            <p>
     *            n = 2, 右对齐
     *            </p>
     */
    static public byte[] getCmdAlignType(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x61, (byte) n
        };
    }

    /**
     * 设置行间距为 n 点行。 默认值行间距是 32 点。
     * 
     * @param n :0-255
     * @return
     */
    static public byte[] getCmdVerticalSpacing(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x33, (byte) n
        };
    }

    /**
     * 用于设置打印字符的方式。默认值是 0
     * 
     * @param n 位 0:保留 位 1:字体反白 位 2:1:字体上下倒置 位 3:1:字体加粗 位 4:1:双倍高度 位 5:1:双倍宽度 位
     *            6:1:删除线
     * @return
     */
    static public byte[] getCmdType(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x21, (byte) n
        };
    }

    /**
     * 初始化指令
     */
    public static byte[] getCmdClear() {
        return new byte[] {
                (byte) 0x1b, (byte) 0x40
        };
    }

    /**
     * 选择HRI字符的打印位置 打印条形码时选择HRI字符的打印位置 n 选择打印位置如下图所示:<br/>
     * ***************************<br/>
     * n 打印位置<br/>
     * 0,48 不打印<br/>
     * 1,49 在条形码上方<br/>
     * 2,50 在条形码下方<br/>
     * 3,51 在条形码的上方及下方<br/>
     * **************************<br/>
     * HRI 表示可阅读的条形码对应字符。
     * 
     * @param byte n : [范围] 0 <=n <=3, 48 <=n <=51 [缺省值] n = 0
     */
    public static byte[] getBarcodeHRILocation(int n) {
        return new byte[] {
                (byte) 0x1D, (byte) 0x48, (byte) n
        };
    }

    /**
     * 设置条形码高度。 n 设定垂直方向的点数。
     * 
     * @param byte n : [范围] 1 <=n <=255 [缺省值] n = 162
     */
    public static byte[] getBarcodeHeight(byte n) {
        return new byte[] {
                0x1D, 0x68, n
        };
    }

    /**
     * 设置条形码水平尺寸。<br/>
     * n 设定条形码宽度如下：<br/>
     * ***********************************************************<br/>
     * n 多级条形码单位<br/>
     * 宽度(毫米) 二进制条形码 窄条宽度(毫米) 宽条宽度(毫米)<br/>
     * 2 0.250 0.250 0.625<br/>
     * 3 0.375 0.375 1.000<br/>
     * 4 0.560 0.500 1.250<br/>
     * 5 0.625 0.625 1.625<br/>
     * 6 0.750 0.750 2.000<br/>
     * 以下是多级条形码：<br/>
     * UPC-A, UPC-E, JAN13 (EAN13), JAN8 (EAN8), CODE93, CODE128<br/>
     * 以下是二进制条形码：<br/>
     * CODE39, ITF, CODABAR<br/>
     * 
     * @param byte n : 2 <=n <=6 [缺省值] n = 3
     */
    public static byte[] getBarcodeWidth(byte n) {
        return new byte[] {
                0x1D, 0x77, n
        };
    }

    /**
     * 选定条形码系统并打印条形码。
     * 
     * @param
     * @return
     * @exception
     */
    public static byte[] getBarcode(int barcodeType) {
        // dns = new byte[]{};
        byte[] cmds = new byte[] {
                0x1D, 0x6B, (byte) barcodeType
        };
        return cmds;
    }

    /**
     * 设置条码打印左边间距 打印条码的起始位置是: 0-->255
     * 
     * @param byte n : [范围] 0-->255
     */
    public static byte[] getBarcodeLeftMargin(byte n) {
        return new byte[] {
                0x1d, 0x78, n
        };
    }
}
