import java.util.*;
import java.io.*;

public class GenStateMachine {
    public static boolean isInteger(String s) {
        Scanner sc = new Scanner(s.trim());
        if (!sc.hasNextInt(10)) {
            sc.close();
            return false;
        }

        sc.nextInt();
        boolean ret = !sc.hasNextInt(10);
        sc.close();

        return ret;
    }

    public static int n, m;
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Invalid format: java GenStateMachine [n] [m] [optional:name]");
            return;
        }

        if (!isInteger(args[0]) || !isInteger(args[1])) {
            System.out.println("Invalid format: expected integers as inputs");
            return;
        }

        n = Integer.parseInt(args[0]);
        m = Integer.parseInt(args[1]);

        try {
            String fileName = "../StateMachine_" + n + "x" + m + ".dig";
            if (args.length > 2) fileName = "../" + args[2] + ".dig";

            PrintWriter pw = new PrintWriter(new File(fileName));
            pw.println(
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + 
                "<circuit>\n" +
                "  <version>2</version>\n" + 
                "  <attributes>\n" + 
                "    <entry>\n" +
                "      <string>romContent</string>\n" +
                "      <romList>\n" +
                "        <roms/>\n" +
                "      </romList>\n" +
                "    </entry>\n" +
                "    <entry>\n" +
                "      <string>Width</string>\n" +
                "      <int>6</int>\n" +
                "    </entry>" +
                "   </attributes>" +
                "  <visualElements>"
            );

            for (int x=0; x<m; x++) {
                for (int y=0; y<n; y++) {
                    NodeGenerator ng = new NodeGenerator(x, y);
                    ng.printVisualElements(pw);

                    ButtonLEDGenerator blg = new ButtonLEDGenerator(x+y*m, 80-40*m+40*x, 320+40*y);
                    blg.printVisualElements(pw);
                }
            }
            ClockGenerator.printVisualElements(pw);

            pw.println(
                "  </visualElements>\n" + 
                "  <wires>"
            );

            for (int x=0; x<m; x++) {
                for (int y=0; y<n; y++) {
                    NodeGenerator g = new NodeGenerator(x, y);
                    g.printWires(pw);
                }
            }
            ClockGenerator.printWires(pw);

            pw.print(
                "  </wires>\n" +
                "  <measurementOrdering/>\n" +
                "</circuit>"
            );

            pw.close();
            System.out.println("Printed? " + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class NodeGenerator {
    private int x, y;
    private int dx, dy;
    private int n, m;

    public NodeGenerator(int x, int y) {
        this.x = x;
        this.y = y;
        this.dx = 29*20*x;
        this.dy = 12*20*y;
        this.n = GenStateMachine.n;
        this.m = GenStateMachine.m;
    }

    public void printVisualElements(PrintWriter pw) {
        pw.println(
            "    <visualElement>\n" + //
            "      <elementName>ConwayNextState.dig</elementName>\n" + //
            "      <elementAttributes/>\n" + //
            "      <pos x=\""+(dx+500)+"\" y=\""+(dy+220)+"\"/>\n" + //
            "    </visualElement>\n" + //
            "    <visualElement>\n" + //
            "      <elementName>Or</elementName>\n" + //
            "      <elementAttributes>\n" + //
            "        <entry>\n" + //
            "          <string>wideShape</string>\n" + //
            "          <boolean>true</boolean>\n" + //
            "        </entry>\n" + //
            "      </elementAttributes>\n" + //
            "      <pos x=\""+(dx+300)+"\" y=\""+(dy+260)+"\"/>\n" + //
            "    </visualElement>\n" + //
            "    <visualElement>\n" + //
            "      <elementName>Tunnel</elementName>\n" + //
            "      <elementAttributes>\n" + //
            "        <entry>\n" + //
            "          <string>rotation</string>\n" + //
            "          <rotation rotation=\"2\"/>\n" + //
            "        </entry>\n" + //
            "        <entry>\n" + //
            "          <string>NetName</string>\n" + //
            "          <string>CLK_EN</string>\n" + //
            "        </entry>\n" + //
            "      </elementAttributes>\n" + //
            "      <pos x=\""+(dx+180)+"\" y=\""+(dy+240)+"\"/>\n" + //
            "    </visualElement>\n" + //
            "    <visualElement>\n" + //
            "      <elementName>Splitter</elementName>\n" + //
            "      <elementAttributes>\n" + //
            "        <entry>\n" + //
            "          <string>rotation</string>\n" + //
            "          <rotation rotation=\"3\"/>\n" + //
            "        </entry>\n" + //
            "        <entry>\n" + //
            "          <string>Input Splitting</string>\n" + //
            "          <string>1*8</string>\n" + //
            "        </entry>\n" + //
            "      </elementAttributes>\n" + //
            "      <pos x=\""+(dx+480)+"\" y=\""+(dy+200)+"\"/>\n" + //
            "    </visualElement>\n" + //
            "    <visualElement>\n" + //
            "      <elementName>T_FF</elementName>\n" + //
            "      <elementAttributes/>\n" + //
            "      <pos x=\""+(dx+400)+"\" y=\""+(dy+260)+"\"/>\n" + //
            "    </visualElement>\n" + //
            "    <visualElement>\n" + //
            "      <elementName>And</elementName>\n" + //
            "      <elementAttributes>\n" + //
            "        <entry>\n" + //
            "          <string>wideShape</string>\n" + //
            "          <boolean>true</boolean>\n" + //
            "        </entry>\n" + //
            "      </elementAttributes>\n" + //
            "      <pos x=\""+(dx+200)+"\" y=\""+(dy+240)+"\"/>\n" + //
            "    </visualElement>\n" + //
            "    <visualElement>\n" + //
            "      <elementName>VDD</elementName>\n" + //
            "      <elementAttributes>\n" + //
            "        <entry>\n" + //
            "          <string>rotation</string>\n" + //
            "          <rotation rotation=\"1\"/>\n" + //
            "        </entry>\n" + //
            "      </elementAttributes>\n" + //
            "      <pos x=\""+(dx+380)+"\" y=\""+(dy+260)+"\"/>\n" + //
            "    </visualElement>\n" + //
            "    <visualElement>\n" + //
            "      <elementName>Tunnel</elementName>\n" + //
            "      <elementAttributes>\n" + //
            "        <entry>\n" + //
            "          <string>rotation</string>\n" + //
            "          <rotation rotation=\"3\"/>\n" + //
            "        </entry>\n" + //
            "        <entry>\n" + //
            "          <string>NetName</string>\n" + //
            "          <string>OUT_"+(x+y*m)+"</string>\n" + //
            "        </entry>\n" + //
            "      </elementAttributes>\n" + //
            "      <pos x=\""+(dx+480)+"\" y=\""+(dy+280)+"\"/>\n" + //
            "    </visualElement>\n" + //
            "    <visualElement>\n" + //
            "      <elementName>Tunnel</elementName>\n" + //
            "      <elementAttributes>\n" + //
            "        <entry>\n" + //
            "          <string>rotation</string>\n" + //
            "          <rotation rotation=\"2\"/>\n" + //
            "        </entry>\n" + //
            "        <entry>\n" + //
            "          <string>NetName</string>\n" + //
            "          <string>TGL_"+(x+y*m)+"</string>\n" + //
            "        </entry>\n" + //
            "      </elementAttributes>\n" + //
            "      <pos x=\""+(dx+280)+"\" y=\""+(dy+300)+"\"/>\n" + //
            "    </visualElement>\n" + //
            "    <visualElement>\n" + //
            "      <elementName>Tunnel</elementName>\n" + //
            "      <elementAttributes>\n" + //
            "        <entry>\n" + //
            "          <string>rotation</string>\n" + //
            "          <rotation rotation=\"1\"/>\n" + //
            "        </entry>\n" + //
            "        <entry>\n" + //
            "          <string>NetName</string>\n" + //
            "          <string>OUT_"+((x+1)%m+y*m)+"</string>\n" + // right
            "        </entry>\n" + //
            "      </elementAttributes>\n" + //
            "      <pos x=\""+(dx+480)+"\" y=\""+(dy+200)+"\"/>\n" + //
            "    </visualElement>\n" + //
            "    <visualElement>\n" + //
            "      <elementName>Tunnel</elementName>\n" + //
            "      <elementAttributes>\n" + //
            "        <entry>\n" + //
            "          <string>rotation</string>\n" + //
            "          <rotation rotation=\"1\"/>\n" + //
            "        </entry>\n" + //
            "        <entry>\n" + //
            "          <string>NetName</string>\n" + //
            "          <string>OUT_"+((x+1)%m+(y+1)%n*m)+"</string>\n" + // right-down
            "        </entry>\n" + //
            "      </elementAttributes>\n" + //
            "      <pos x=\""+(dx+460)+"\" y=\""+(dy+200)+"\"/>\n" + //
            "    </visualElement>\n" + //
            "    <visualElement>\n" + //
            "      <elementName>Tunnel</elementName>\n" + //
            "      <elementAttributes>\n" + //
            "        <entry>\n" + //
            "          <string>rotation</string>\n" + //
            "          <rotation rotation=\"1\"/>\n" + //
            "        </entry>\n" + //
            "        <entry>\n" + //
            "          <string>NetName</string>\n" + //
            "          <string>OUT_"+(x+(y+1)%n*m)+"</string>\n" + // down
            "        </entry>\n" + //
            "      </elementAttributes>\n" + //
            "      <pos x=\""+(dx+440)+"\" y=\""+(dy+200)+"\"/>\n" + //
            "    </visualElement>\n" + //
            "    <visualElement>\n" + //
            "      <elementName>Tunnel</elementName>\n" + //
            "      <elementAttributes>\n" + //
            "        <entry>\n" + //
            "          <string>rotation</string>\n" + //
            "          <rotation rotation=\"1\"/>\n" + //
            "        </entry>\n" + //
            "        <entry>\n" + //
            "          <string>NetName</string>\n" + //
            "          <string>OUT_"+((x+m-1)%m+(y+1)%n*m)+"</string>\n" + // left-down
            "        </entry>\n" + //
            "      </elementAttributes>\n" + //
            "      <pos x=\""+(dx+420)+"\" y=\""+(dy+200)+"\"/>\n" + //
            "    </visualElement>\n" + //
            "    <visualElement>\n" + //
            "      <elementName>Tunnel</elementName>\n" + //
            "      <elementAttributes>\n" + //
            "        <entry>\n" + //
            "          <string>rotation</string>\n" + //
            "          <rotation rotation=\"1\"/>\n" + //
            "        </entry>\n" + //
            "        <entry>\n" + //
            "          <string>NetName</string>\n" + //
            "          <string>OUT_"+((x+m-1)%m+y*m)+"</string>\n" + // left
            "        </entry>\n" + //
            "      </elementAttributes>\n" + //
            "      <pos x=\""+(dx+400)+"\" y=\""+(dy+200)+"\"/>\n" + //
            "    </visualElement>\n" + //
            "    <visualElement>\n" + //
            "      <elementName>Tunnel</elementName>\n" + //
            "      <elementAttributes>\n" + //
            "        <entry>\n" + //
            "          <string>rotation</string>\n" + //
            "          <rotation rotation=\"1\"/>\n" + //
            "        </entry>\n" + //
            "        <entry>\n" + //
            "          <string>NetName</string>\n" + //
            "          <string>OUT_"+((x+m-1)%m+(y+n-1)%n*m)+"</string>\n" + // left-up
            "        </entry>\n" + //
            "      </elementAttributes>\n" + //
            "      <pos x=\""+(dx+380)+"\" y=\""+(dy+200)+"\"/>\n" + //
            "    </visualElement>\n" + //
            "    <visualElement>\n" + //
            "      <elementName>Tunnel</elementName>\n" + //
            "      <elementAttributes>\n" + //
            "        <entry>\n" + //
            "          <string>rotation</string>\n" + //
            "          <rotation rotation=\"1\"/>\n" + //
            "        </entry>\n" + //
            "        <entry>\n" + //
            "          <string>NetName</string>\n" + //
            "          <string>OUT_"+(x+(y+n-1)%n*m)+"</string>\n" + // up
            "        </entry>\n" + //
            "      </elementAttributes>\n" + //
            "      <pos x=\""+(dx+360)+"\" y=\""+(dy+200)+"\"/>\n" + //
            "    </visualElement>\n" + //
            "    <visualElement>\n" + //
            "      <elementName>Tunnel</elementName>\n" + //
            "      <elementAttributes>\n" + //
            "        <entry>\n" + //
            "          <string>rotation</string>\n" + //
            "          <rotation rotation=\"1\"/>\n" + //
            "        </entry>\n" + //
            "        <entry>\n" + //
            "          <string>NetName</string>\n" + //
            "          <string>OUT_"+((x+1)%m+(y+n-1)%n*m)+"</string>\n" + // right-up
            "        </entry>\n" + //
            "      </elementAttributes>\n" + //
            "      <pos x=\""+(dx+340)+"\" y=\""+(dy+200)+"\"/>\n" + //
            "    </visualElement>\n" + //
            "    <visualElement>\n" + //
            "      <elementName>D_FF</elementName>\n" + //
            "      <elementAttributes>\n" + //
            "        <entry>\n" + //
            "          <string>inverterConfig</string>\n" + //
            "          <inverterConfig>\n" + //
            "            <string>C</string>\n" + //
            "          </inverterConfig>\n" + //
            "        </entry>\n" + //
            "      </elementAttributes>\n" + //
            "      <pos x=\""+(dx+600)+"\" y=\""+(dy+240)+"\"/>\n" + //
            "    </visualElement>\n" + //
            "    <visualElement>\n" + //
            "      <elementName>Tunnel</elementName>\n" + //
            "      <elementAttributes>\n" + //
            "        <entry>\n" + //
            "          <string>rotation</string>\n" + //
            "          <rotation rotation=\"2\"/>\n" + //
            "        </entry>\n" + //
            "        <entry>\n" + //
            "          <string>NetName</string>\n" + //
            "          <string>CLK_T</string>\n" + //
            "        </entry>\n" + //
            "      </elementAttributes>\n" + //
            "      <pos x=\""+(dx+580)+"\" y=\""+(dy+260)+"\"/>\n" + //
            "    </visualElement>"
        );
    }

    public void printWires(PrintWriter pw) {
        pw.println(
            "    <wire>\n" + //
            "      <p1 x=\""+(dx+180)+"\" y=\""+(dy+240)+"\"/>\n" + //
            "      <p2 x=\""+(dx+200)+"\" y=\""+(dy+240)+"\"/>\n" + //
            "    </wire>\n" + //
            "    <wire>\n" + //
            "      <p1 x=\""+(dx+560)+"\" y=\""+(dy+240)+"\"/>\n" + //
            "      <p2 x=\""+(dx+600)+"\" y=\""+(dy+240)+"\"/>\n" + //
            "    </wire>\n" + //
            "    <wire>\n" + //
            "      <p1 x=\""+(dx+660)+"\" y=\""+(dy+240)+"\"/>\n" + //
            "      <p2 x=\""+(dx+680)+"\" y=\""+(dy+240)+"\"/>\n" + //
            "    </wire>\n" + //
            "    <wire>\n" + //
            "      <p1 x=\""+(dx+240)+"\" y=\""+(dy+240)+"\"/>\n" + //
            "      <p2 x=\""+(dx+540)+"\" y=\""+(dy+240)+"\"/>\n" + //
            "    </wire>\n" + //
            "    <wire>\n" + //
            "      <p1 x=\""+(dx+460)+"\" y=\""+(dy+260)+"\"/>\n" + //
            "      <p2 x=\""+(dx+480)+"\" y=\""+(dy+260)+"\"/>\n" + //
            "    </wire>\n" + //
            "    <wire>\n" + //
            "      <p1 x=\""+(dx+280)+"\" y=\""+(dy+260)+"\"/>\n" + //
            "      <p2 x=\""+(dx+300)+"\" y=\""+(dy+260)+"\"/>\n" + //
            "    </wire>\n" + //
            "    <wire>\n" + //
            "      <p1 x=\""+(dx+380)+"\" y=\""+(dy+260)+"\"/>\n" + //
            "      <p2 x=\""+(dx+400)+"\" y=\""+(dy+260)+"\"/>\n" + //
            "    </wire>\n" + //
            "    <wire>\n" + //
            "      <p1 x=\""+(dx+480)+"\" y=\""+(dy+260)+"\"/>\n" + //
            "      <p2 x=\""+(dx+500)+"\" y=\""+(dy+260)+"\"/>\n" + //
            "    </wire>\n" + //
            "    <wire>\n" + //
            "      <p1 x=\""+(dx+380)+"\" y=\""+(dy+280)+"\"/>\n" + //
            "      <p2 x=\""+(dx+400)+"\" y=\""+(dy+280)+"\"/>\n" + //
            "    </wire>\n" + //
            "    <wire>\n" + //
            "      <p1 x=\""+(dx+200)+"\" y=\""+(dy+280)+"\"/>\n" + //
            "      <p2 x=\""+(dx+240)+"\" y=\""+(dy+280)+"\"/>\n" + //
            "    </wire>\n" + //
            "    <wire>\n" + //
            "      <p1 x=\""+(dx+480)+"\" y=\""+(dy+220)+"\"/>\n" + //
            "      <p2 x=\""+(dx+500)+"\" y=\""+(dy+220)+"\"/>\n" + //
            "    </wire>\n" + //
            "    <wire>\n" + //
            "      <p1 x=\""+(dx+540)+"\" y=\""+(dy+220)+"\"/>\n" + //
            "      <p2 x=\""+(dx+680)+"\" y=\""+(dy+220)+"\"/>\n" + //
            "    </wire>\n" + //
            "    <wire>\n" + //
            "      <p1 x=\""+(dx+280)+"\" y=\""+(dy+300)+"\"/>\n" + //
            "      <p2 x=\""+(dx+300)+"\" y=\""+(dy+300)+"\"/>\n" + //
            "    </wire>\n" + //
            "    <wire>\n" + //
            "      <p1 x=\""+(dx+240)+"\" y=\""+(dy+240)+"\"/>\n" + //
            "      <p2 x=\""+(dx+240)+"\" y=\""+(dy+280)+"\"/>\n" + //
            "    </wire>\n" + //
            "    <wire>\n" + //
            "      <p1 x=\""+(dx+480)+"\" y=\""+(dy+260)+"\"/>\n" + //
            "      <p2 x=\""+(dx+480)+"\" y=\""+(dy+280)+"\"/>\n" + //
            "    </wire>\n" + //
            "    <wire>\n" + //
            "      <p1 x=\""+(dx+680)+"\" y=\""+(dy+220)+"\"/>\n" + //
            "      <p2 x=\""+(dx+680)+"\" y=\""+(dy+240)+"\"/>\n" + //
            "    </wire>\n" + //
            "    <wire>\n" + //
            "      <p1 x=\""+(dx+540)+"\" y=\""+(dy+220)+"\"/>\n" + //
            "      <p2 x=\""+(dx+540)+"\" y=\""+(dy+240)+"\"/>\n" + //
            "    </wire>"
        );
    }
}

class ClockGenerator {
    public static void printVisualElements(PrintWriter pw) {
        pw.println(
            "    <visualElement>\n" + //
            "      <elementName>Clock</elementName>\n" + //
            "      <elementAttributes>\n" + //
            "        <entry>\n" + //
            "          <string>runRealTime</string>\n" + //
            "          <boolean>true</boolean>\n" + //
            "        </entry>" + //
            "        <entry>\n" + //
            "          <string>Label</string>\n" + //
            "          <string>CLK</string>\n" + //
            "        </entry>\n" + //
            "        <entry>\n" + //
            "          <string>Frequency</string>\n" + //
            "          <int>2</int>\n" + //
            "        </entry>" + //
            "      </elementAttributes>\n" + //
            "      <pos x=\"-180\" y=\"220\"/>\n" + //
            "    </visualElement>\n" + //
            "    <visualElement>\n" + //
            "      <elementName>In</elementName>\n" + //
            "      <elementAttributes>\n" + //
            "        <entry>\n" + //
            "          <string>Label</string>\n" + //
            "          <string>ENABLE</string>\n" + //
            "        </entry>\n" + //
            "      </elementAttributes>\n" + //
            "      <pos x=\"-180\" y=\"260\"/>\n" + //
            "    </visualElement>\n" + //
            "    <visualElement>\n" + //
            "      <elementName>And</elementName>\n" + //
            "      <elementAttributes>\n" + //
            "        <entry>\n" + //
            "          <string>wideShape</string>\n" + //
            "          <boolean>true</boolean>\n" + //
            "        </entry>\n" + //
            "      </elementAttributes>\n" + //
            "      <pos x=\"-140\" y=\"220\"/>\n" + //
            "    </visualElement>\n" + //
            "    <visualElement>\n" + //
            "      <elementName>Tunnel</elementName>\n" + //
            "      <elementAttributes>\n" + //
            "        <entry>\n" + //
            "          <string>NetName</string>\n" + //
            "          <string>CLK_EN</string>\n" + //
            "        </entry>\n" + //
            "      </elementAttributes>\n" + //
            "      <pos x=\"-40\" y=\"240\"/>\n" + //
            "    </visualElement>\n" + //
            "    <visualElement>\n" + //
            "      <elementName>Tunnel</elementName>\n" + //
            "      <elementAttributes>\n" + //
            "        <entry>\n" + //
            "          <string>rotation</string>\n" + //
            "          <rotation rotation=\"1\"/>\n" + //
            "        </entry>\n" + //
            "        <entry>\n" + //
            "          <string>NetName</string>\n" + //
            "          <string>CLK_T</string>\n" + //
            "        </entry>\n" + //
            "      </elementAttributes>\n" + //
            "      <pos x=\"-160\" y=\"200\"/>\n" + //
            "    </visualElement>"
        );
    }

    public static void printWires(PrintWriter pw) {
        pw.println(
            "    <wire>\n" + //
            "      <p1 x=\"-60\" y=\"240\"/>\n" + //
            "      <p2 x=\"-40\" y=\"240\"/>\n" + //
            "    </wire>\n" + //
            "    <wire>\n" + //
            "      <p1 x=\"-180\" y=\"260\"/>\n" + //
            "      <p2 x=\"-140\" y=\"260\"/>\n" + //
            "    </wire>\n" + //
            "    <wire>\n" + //
            "      <p1 x=\"-180\" y=\"220\"/>\n" + //
            "      <p2 x=\"-160\" y=\"220\"/>\n" + //
            "    </wire>\n" + //
            "    <wire>\n" + //
            "      <p1 x=\"-160\" y=\"220\"/>\n" + //
            "      <p2 x=\"-140\" y=\"220\"/>\n" + //
            "    </wire>\n" + //
            "    <wire>\n" + //
            "      <p1 x=\"-160\" y=\"200\"/>\n" + //
            "      <p2 x=\"-160\" y=\"220\"/>\n" + //
            "    </wire>"
        );
    }
}

class ToggleGenerator { // DEPRECATED
    private String name;
    private int x, y;
    private int dx, dy;
    private int tunnel_delta;

    public ToggleGenerator(String name, int x, int y, int dx, int dy, int tunnel_delta) throws IllegalArgumentException {
        if (x * y > 64) 
            throw new IllegalArgumentException("Illegal size for ToggleGenerator (max area 64)");

        this.name = name;
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.tunnel_delta = tunnel_delta;
    }

    public void printVisualElements(PrintWriter pw) {
        pw.println(
            "   <visualElement>\n" + //
            "      <elementName>In</elementName>\n" + //
            "      <elementAttributes>\n" + //
            "        <entry>\n" + //
            "          <string>Label</string>\n" + //
            "          <string>" + name + "</string>\n" + //
            "        </entry>\n" + //
            "        <entry>\n" + //
            "          <string>Bits</string>\n" + //
            "          <int>" + (x * y) + "</int>\n" + //
            "        </entry>\n" + //
            "      </elementAttributes>\n" + //
            "      <pos x=\"" + dx + "\" y=\"" + dy + "\"/>\n" + // default: -320, 520
            "    </visualElement>\n" + //
            "    <visualElement>\n" + //
            "      <elementName>Splitter</elementName>\n" + //
            "      <elementAttributes>\n" + //
            "        <entry>\n" + //
            "          <string>splitterSpreading</string>\n" + //
            "          <int>3</int>\n" + //
            "        </entry>\n" + //
            "        <entry>\n" + //
            "          <string>Input Splitting</string>\n" + //
            "          <string>" + (x * y) + "</string>\n" + //
            "        </entry>\n" + //
            "        <entry>\n" + //
            "          <string>Output Splitting</string>\n" + //
            "          <string>" + x + "*" + y + "</string>\n" + //
            "        </entry>\n" + //
            "      </elementAttributes>\n" + //
            "      <pos x=\"" + dx + "\" y=\"" + dy + "\"/>\n" + //
            "    </visualElement>"
        );

        for (int i=0; i<y; i++) {
            pw.println(
                "    <visualElement>\n" + //
                "      <elementName>Splitter</elementName>\n" + //
                "      <elementAttributes>\n" + //
                "        <entry>\n" + //
                "          <string>mirror</string>\n" + //
                "          <boolean>true</boolean>\n" + //
                "        </entry>\n" + //
                "        <entry>\n" + //
                "          <string>splitterSpreading</string>\n" + //
                "          <int>2</int>\n" + //
                "        </entry>\n" + //
                "        <entry>\n" + //
                "          <string>rotation</string>\n" + //
                "          <rotation rotation=\"3\"/>\n" + //
                "        </entry>\n" + //
                "        <entry>\n" + //
                "          <string>Input Splitting</string>\n" + //
                "          <string>" + x + "</string>\n" + //
                "        </entry>\n" + //
                "        <entry>\n" + //
                "          <string>Output Splitting</string>\n" + //
                "          <string>1*" + x + "</string>\n" + //
                "        </entry>\n" + //
                "      </elementAttributes>\n" + //
                "      <pos x=\"" + (dx+20) + "\" y=\"" + (dy+i*60) + "\"/>\n" + //
                "    </visualElement>"
            );
        }
    
        for (int i=0; i<y; i++) {
            for (int j=0; j<x; j++) {
                pw.println(
                    "    <visualElement>\n" + //
                    "      <elementName>Tunnel</elementName>\n" + //
                    "      <elementAttributes>\n" + //
                    "        <entry>\n" + //
                    "          <string>rotation</string>\n" + //
                    "          <rotation rotation=\"3\"/>\n" + //
                    "        </entry>\n" + //
                    "        <entry>\n" + //
                    "          <string>NetName</string>\n" + //
                    "          <string>TGL_" + (j+i*y+tunnel_delta) + "</string>\n" + //
                    "        </entry>\n" + //
                    "      </elementAttributes>\n" + //
                    "      <pos x=\"" + (dx+20+40*j) + "\" y=\"" + (dy+20+60*i) + "\"/>\n" + //
                    "    </visualElement>"
                );
            }
        }
    }
}

class OutGenerator { // DEPRECATED
    private String name;
    private int x, y;
    private int dx, dy;
    private int tunnel_delta;

    public OutGenerator(String name, int x, int y, int dx, int dy, int tunnel_delta) throws IllegalArgumentException {
        if (x * y > 64) 
            throw new IllegalArgumentException("Illegal size for OutGenerator (max area 64)");

        this.name = name;
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.tunnel_delta = tunnel_delta;
    }

    public void printVisualElements(PrintWriter pw) {
        pw.println(
            "    <visualElement>\n" + //
            "      <elementName>Splitter</elementName>\n" + //
            "      <elementAttributes>\n" + //
            "        <entry>\n" + //
            "          <string>splitterSpreading</string>\n" + //
            "          <int>3</int>\n" + //
            "        </entry>\n" + //
            "        <entry>\n" + //
            "          <string>Input Splitting</string>\n" + //
            "          <string>" + x + "*" + y + "</string>\n" + //
            "        </entry>\n" + //
            "        <entry>\n" + //
            "          <string>Output Splitting</string>\n" + //
            "          <string>" + (x * y) + "</string>\n" + //
            "        </entry>\n" + //
            "      </elementAttributes>\n" + //
            "      <pos x=\"" + (dx-20) + "\" y=\"" + dy + "\"/>\n" + //
            "    </visualElement>\n" + //
            "    <visualElement>\n" + //
            "      <elementName>Out</elementName>\n" + //
            "      <elementAttributes>\n" + //
            "        <entry>\n" + //
            "          <string>rotation</string>\n" + //
            "          <rotation rotation=\"1\"/>\n" + //
            "        </entry>\n" + //
            "        <entry>\n" + //
            "          <string>Label</string>\n" + //
            "          <string>" + name + "</string>\n" + //
            "        </entry>\n" + //
            "        <entry>\n" + //
            "          <string>Bits</string>\n" + //
            "          <int>" + (x * y) + "</int>\n" + //
            "        </entry>\n" + //
            "      </elementAttributes>\n" + //
            "      <pos x=\"" + dx + "\" y=\"" + dy + "\"/>\n" + //
            "    </visualElement>"
        );

        for (int i=0; i<y; i++) {
            pw.println(
                "    <visualElement>\n" + //
                "      <elementName>Splitter</elementName>\n" + //
                "      <elementAttributes>\n" + //
                "        <entry>\n" + //
                "          <string>splitterSpreading</string>\n" + //
                "          <int>2</int>\n" + //
                "        </entry>\n" + //
                "        <entry>\n" + //
                "          <string>rotation</string>\n" + //
                "          <rotation rotation=\"1\"/>\n" + //
                "        </entry>\n" + //
                "        <entry>\n" + //
                "          <string>Input Splitting</string>\n" + //
                "          <string>1*" + x + "</string>\n" + //
                "        </entry>\n" + //
                "        <entry>\n" + //
                "          <string>Output Splitting</string>\n" + //
                "          <string>" + x + "</string>\n" + //
                "        </entry>\n" + //
                "      </elementAttributes>\n" + //
                "      <pos x=\"" + (dx-20) + "\" y=\"" + (dy+20+60*i) + "\"/>\n" + //
                "    </visualElement>"
            );
        }
    
        for (int i=0; i<y; i++) {
            for (int j=0; j<x; j++) {
                pw.println(
                    "    <visualElement>\n" + //
                    "      <elementName>Tunnel</elementName>\n" + //
                    "      <elementAttributes>\n" + //
                    "        <entry>\n" + //
                    "          <string>rotation</string>\n" + //
                    "          <rotation rotation=\"3\"/>\n" + //
                    "        </entry>\n" + //
                    "        <entry>\n" + //
                    "          <string>NetName</string>\n" + //
                    "          <string>OUT_" + (j+i*x+tunnel_delta) + "</string>\n" + //
                    "        </entry>\n" + //
                    "      </elementAttributes>\n" + //
                    "      <pos x=\"" + (dx-20+40*j) + "\" y=\"" + (dy+20+60*i) + "\"/>\n" + //
                    "    </visualElement>"
                );
            }
        }
    }
}

class ButtonLEDGenerator {
    private int id;
    private int dx, dy;

    public ButtonLEDGenerator(int id, int dx, int dy) {
        this.id = id;
        this.dx = dx;
        this.dy = dy;
    }

    public void printVisualElements(PrintWriter pw) {
        pw.println(
            "    <visualElement>\n" + //
            "      <elementName>LED</elementName>\n" + //
            "      <elementAttributes>\n" + //
            "        <entry>\n" + //
            "          <string>rotation</string>\n" + //
            "          <rotation rotation=\"3\"/>\n" + //
            "        </entry>\n" + //
            "      </elementAttributes>\n" + //
            "      <pos x=\"" + dx + "\" y=\"" + dy + "\"/>\n" + //
            "    </visualElement>\n" + //
            "    <visualElement>\n" + //
            "      <elementName>Button</elementName>\n" + //
            "      <elementAttributes/>\n" + //
            "      <pos x=\"" + (dx+20) + "\" y=\"" + (dy+20) + "\"/>\n" + //
            "    </visualElement>\n" + //
            "    <visualElement>\n" + //
            "      <elementName>Tunnel</elementName>\n" + //
            "      <elementAttributes>\n" + //
            "        <entry>\n" + //
            "          <string>NetName</string>\n" + //
            "          <string>TGL_" + id + "</string>\n" + //
            "        </entry>\n" + //
            "      </elementAttributes>\n" + //
            "      <pos x=\"" + (dx+20) + "\" y=\"" + (dy+20) + "\"/>\n" + //
            "    </visualElement>\n" + //
            "    <visualElement>\n" + //
            "      <elementName>Tunnel</elementName>\n" + //
            "      <elementAttributes>\n" + //
            "        <entry>\n" + //
            "          <string>NetName</string>\n" + //
            "          <string>OUT_" + id + "</string>\n" + //
            "        </entry>\n" + //
            "      </elementAttributes>\n" + //
            "      <pos x=\"" + dx + "\" y=\"" + dy + "\"/>\n" + //
            "    </visualElement>"
        );
    }
}