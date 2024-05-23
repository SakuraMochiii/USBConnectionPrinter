
package com.wizarpos.usbconnectionprinter;

public class PrinterCommand1
{
    /*--------------------------Print commands-----------------------------*/

    /**
     * Print the contents of the line buffer and feed one line. If the line buffer is empty, just feed one line.
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
     * Move the print position to the next tab, with each tab being the start of an 8 character group.
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
     * Print the data in the buffer. If the black mark feature is available, move to the next black mark position after printing
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
     * Print the contents of the line buffer and move forward by n lines. 
     * This command is only effective for the current line and does not change the line spacing value set by the ESC 2 and ESC 3 commands.
     * 
     * @param n: 0-255
     * @return
     */
    static public byte[] getCmdEscJN(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x4A, (byte) n
        };
    }

    /**
     * Print the data in the buffer. If the mark feature is available, move to the next mark position after printing
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
     * Print the contents of the line buffer and move forward by n lines. 
     * This command is only effective for the current line and does not change the line spacing value set by the ESC 2 and ESC 3 commands.
     * 
     * @param n: 0-255
     * @return
     */
    static public byte[] getCmdEscDN(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x64, (byte) n
        };
    }

    /**
     * Set Chinese small font.
     */
    static public byte[] getCmdSetSmallFont_CN()
    {
        return new byte[] {
                (byte) 0x1C, (byte) 0x21, (byte) 0x01
        };
    }

    /**
     * Cancel Chinese small font.
     */
    static public byte[] getCmdCancelSmallFont_CN()
    {
        return new byte[] {
                (byte) 0x1C, (byte) 0x21, (byte) 0x00
        };
    }

    /**
     * Set English small font.
     */
    static public byte[] getCmdSetSmallFont_EN()
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x21, (byte) 0x01
        };
    }

    /**
     * Cancel English small font.
     */
    static public byte[] getCmdCancelSmallFont_EN()
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x21, (byte) 0x00
        };
    }

    /**
     * 1: The printer is in online mode, receiving and printing data. 0: The printer is in offline mode, not accepting or printing data.
     * 
     * @param n: 0, 1 with the lowest bit being effective.
     * @return
     */
    static public byte[] getCmdEscN(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x3d, (byte) n
        };
    }

    /*--------------------------Line spacing setting commands-----------------------------*/

    /**
     * Set line spacing to 4 millimeters, 32 dots.
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
     * Set the line spacing to n dot lines. The default line spacing is 32 dots.
     * 
     * @param n: 0-255
     * @return
     */
    static public byte[] getCmdEsc3N(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x33, (byte) n
        };
    }

    /**
     * Set the alignment of printed lines. 
     * Default: Left alignment 0 ≤ n ≤ 2 or 48 ≤ n ≤ 50 
     * Left alignment: n=0,48
     * Center alignment: n=1,49
     * Right alignment: n=2,50
     * 
     * @param n: 0 ≤ n ≤ 2 or 48 ≤ n ≤ 50
     * @return
     */
    static public byte[] getCmdEscAN(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x61, (byte) n
        };
    }

    /**
     * Set the left margin for printing, default is 0. The left margin is nL + nH * 256, unit is 0.125mm.
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
     * Set the left margin for printing, default is 0. The left margin is nL + nH * 256, unit is 0.125mm.
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

    /*--------------------------Character setting commands-----------------------------*/

    /**
     * Set the printing mode for characters. The default value is 0.
     * 
     * @param n bit 0: Reserved 
     * 1: Inverted
     * 2: 1: Upside down
     * 3: 1: Bold
     * 4: 1: Height multiplier
     * 5: 1: Width multiplier
     * 6: 1: Strikethrough
     * @return
     */
    static public byte[] getCmdEsc_N(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x21, (byte) n
        };
    }

    /**
     * The last 4 bits of n indicate whether the height is multiplied. When it equals 0, it is not multiplied. 
     * The first 4 bits of n indicate whether the width is multiplied. When it equals 0, it is not multiplied.
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
     * n=0: Cancel bold font. When not equal to 0, set bold font.
     * 
     * @param Least significant bit of n is valid 
     * @return
     */
    static public byte[] getCmdEscEN(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x45, (byte) n
        };
    }

    /**
     * default: 0
     * 
     * @param n: Spacing between two characters
     * @return
     */
    static public byte[] getCmdEscSpN(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x20, (byte) n
        };
    }

    /**
     * After this command, all characters will be printed at double the normal width
     * Can be canceled using either Enter or DC4 command.
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
     * After this command, the characters return to printing at normal width.
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
     * Default:0
     * 
     * @param n n=1: Set characters to printing upside down, n=0: Cancel upside down
     * @return
     */
    static public byte[] getCmdEsc__N(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x7B, (byte) n
        };
    }

    /**
     * Default:0
     * 
     * @param n n=1: Set characters to print inverted (white characters on black background), n=0: Cancel inverted printing
     * @return
     */
    static public byte[] getCmdGsBN(int n)
    {
        return new byte[] {
                (byte) 0x1D, (byte) 0x42, (byte) n
        };
    }

    /**
     * Default:0
     * 
     * @param n n=0-2: Underline height
     * @return
     */
    static public byte[] getCmdEsc___N(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x2D, (byte) n
        };
    }

    /**
     * @param n n=1: Select user-defined character set, n=0: Select internal character set (default).
     * @return
     */
    static public byte[] getCmdEsc____N(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x25, (byte) n
        };
    }

    /**
     * Set user-defined characters, max 32 characters.
     * 
     * @return
     */
    static public byte[] getCmdEsc_SNMW()
    {
        // blank
        return null;
    }

    /**
     * Cancel user-defined characters. After cancellation, use the internal system's characters.
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
     * Select the international character set. This command is not supported in the Chinese version.
     * 
     * @param n international character set configuration: 
     *            0:USA 1:France 2:Germany 3:U.K. 4:Denmark I 5:Sweden
     *            6:Italy 7:Spain I 8:Japan 9:Norway 10:Denmark II 11:Spain II
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
     * Select the character code page, used to select printable characters from 0x80 to 0xfe. 
     * This command is not supported in the Chinese version.
     * 
     * @param n character code page parameters are as follows: 0:437 1:850
     * @return
     */
    static public byte[] getCmdEscTN(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x74, (byte) n
        };
    }

    /*--------------------------Graphic printing commands omitted-----------------------------*/

    /*--------------------------Key control commands-----------------------------*/

    /**
     * This command for enabling/disabling key switches is temporarily not supported.
     * 
     * @param n n=1: Disable keys n=0: Allow keys (default)
     * @return
     */
    static public byte[] getCmdEscC5N(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x63, (byte) 0x35, (byte) n
        };
    }

    /*--------------------------Initialization command-----------------------------*/

    /**
     * Initialize the printer. Clear the print buffer, restore default values, select character printing mode, delete user-defined characters
     * 
     * @return
     */
    static public byte[] getCmdEsc_()
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x40
        };
    }

    /*--------------------------Status transmission command-----------------------------*/

    /**
     * Transmit control board status to the host
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
     * When enabled, if the printer detects a change in status, it will automatically send the status to the host. 
     * For details, refer to the ESC/POS instruction set.
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
     * Transmit peripheral device status to the host, only applicable to serial printers. 
     * This command is currently not supported. For details, refer to the ESC/POS instruction set.
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

    /*--------------------------Barcode printing commands omitted-----------------------------*/

    /*--------------------------Control board parameter commands omitted-----------------------------*/

    /**
     * Custom tab stop (2 spaces)
     * 
     * @return
     */
    static public byte[] getCustomTabs()
    {
        return "  ".getBytes();
    }

    /**
     * Cut paper command
     */
    static public byte[] getCmdCutPaper()
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x69
        };
    }

    /**
     * Default: 0
     * 
     * @param n Bold: 1, Cancel bold: 0
     */
    static public byte[] getCmdBold(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x45, (byte) n
        };
    }

    /**
     * Default: 0
     * 
     * @param n Reverse: 1, Cancel reverse: 0
     */
    static public byte[] getCmdReverse(int n)
    {
        return new byte[] {
                (byte) 0x1D, (byte) 0x42, (byte) n
        };
    }

    /**
     * Default: 0
     * 
     * @param n Upside down: 1, Cancel upside down: 0
     */
    static public byte[] getCmdInversion(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x7B, (byte) n
        };
    }

    /**
     * Default: 0
     * 
     * @param n Set Chinese small font: 1, Cancel Chinese small font: 0
     */
    static public byte[] getCmdSmallFontCN(int n)
    {
        return new byte[] {
                (byte) 0x1C, (byte) 0x21, (byte) n
        };
    }

    /**
     * Default: 0
     * 
     * @param n Set English small font: 1, Cancel English small font: 0
     */
    static public byte[] getCmdSmallFontEN(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x21, (byte) n
        };
    }

    /**
     * Set font width and height
     * 
     * @param n = 0xAB
     *            A: width multiplier, 0<A<7, representing 1-8 times the width respectively
     *            B: height multiplier, 0<B<7, representing 1-8 times the height respectively
     */
    static public byte[] getCmdFontSize(int n)
    {
        return new byte[] {
                (byte) 0x1D, (byte) 0x21, (byte) n
        };
    }

    /**
     * Alignment
     * 
     * @param n: n=0, Left align
     *           n=1, Center align
     *           n=2, Right align
     */
    static public byte[] getCmdAlignType(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x61, (byte) n
        };
    }

    /**
     * Set line spacing to n dot lines. Default line spacing is 32 dots.
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
     * Set the printing mode for characters. 
     * Default: 0
     * 
     * @param n bit 0: reserved
     * 1: inverted
     * 2: 1: upside down
     * 3: 1: bold
     * 4: 1: height multiplier
     * 5: 1: width multiplier
     * 6: 1: strikethrough
     * @return
     */
    static public byte[] getCmdType(int n)
    {
        return new byte[] {
                (byte) 0x1B, (byte) 0x21, (byte) n
        };
    }

    /**
     * Initialization command
     */
    public static byte[] getCmdClear() {
        return new byte[] {
                (byte) 0x1b, (byte) 0x40
        };
    }

    /**
     * Select the printing position of HRI characters.
     * When printing barcodes, select the printing position of HRI characters
     * n selects the printing position as shown in the diagram below:
     * ***************************
     * n Printing position
     * 0,48 Do not print
     * 1,49 Above the barcode
     * 2,50 Below the barcode
     * 3,51 Above and below the barcode
     * ************************** 
     * HRI indicates the corresponding characters of the barcode that are readable.
     * 
     * @param byte n: [Range] 0 <= n <= 3, 48 <= n <= 51 [Default Value] n = 0
     */
    public static byte[] getBarcodeHRILocation(int n) {
        return new byte[] {
                (byte) 0x1D, (byte) 0x48, (byte) n
        };
    }

    /**
     * Set barcode height. n sets the number of dots in the vertical direction.
     * 
     * @param byte n: [Range] 1 <= n <= 255 [Default Value] n = 162
     */
    public static byte[] getBarcodeHeight(byte n) {
        return new byte[] {
                0x1D, 0x68, n
        };
    }

    /**
     * Set barcode horizontal size.
     * n sets the barcode width as follows:
     * ***********************************************************
     * n Multi-level barcode units
     * Width (mm) Binary barcode Narrow bar width (mm) Wide bar width (mm)
     * 2 0.250 0.250 0.625
     * 3 0.375 0.375 1.000
     * 4 0.560 0.500 1.250
     * 5 0.625 0.625 1.625
     * 6 0.750 0.750 2.000
     * The following are multi-level barcodes:
     * UPC-A, UPC-E, JAN13 (EAN13), JAN8 (EAN8), CODE93, CODE128
     * The following are binary barcodes:
     * CODE39, ITF, CODABAR
     * 
     * @param byte n: 2 <= n <= 6 [Default Value] n = 3
     */
    public static byte[] getBarcodeWidth(byte n) {
        return new byte[] {
                0x1D, 0x77, n
        };
    }

    /**
     * Select barcode system and print barcode
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
     * Set barcode printing left margin. Start position: 0-->255
     * 
     * @param byte n : [Range] 0-->255
     */
    public static byte[] getBarcodeLeftMargin(byte n) {
        return new byte[] {
                0x1d, 0x78, n
        };
    }
}
