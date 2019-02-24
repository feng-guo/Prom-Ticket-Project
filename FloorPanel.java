import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class FloorPanel extends JPanel {
    private double WIDTH;
    private double HEIGHT;
    private double LEFT_PANEL_PERCENT;
    private double TOP_PANEL_PERCENT;
    private double DIVISION_PANEL_PERCENT_TOP;
    private double DIVISION_PANEL_PERCENT_MID;
    private double DIVISION_PANEL_PERCENT_BOT;
    private int BORDER_WIDTH;
    private int TEXT_HEIGHT;
    private int TEXT_LEFT_SHIFT;
    private ArrayList<DrawnTable> drawTables;
    private ArrayList<DrawnStudent> displayStudentOrder;
    private ArrayList<DrawnTable> displayTableOrder;
    private int x;
    private int y;
    private double roomHeight;
    private double roomWidth;
    private int hCircle;
    private int wCircle;
    private int wTwoCircle;
    private int maxTableCapacity;
    private double scalingWidth;
    private double scalingHeight;
    private double scaling;
    private boolean offset;
    private int xCircleCount;
    private CustomMouseAdapter myMouseAdapter;
    private CustomKeyListener myKeyListener;
    private int xAdjust;
    private int yAdjust;
    private int xPerm;
    private int yPerm;
    private boolean justPressed;
    private boolean justPanelPressed;
    private int panX;
    private int panY;
    private boolean clickRefresh;
    private boolean buttonRefresh;
    private int studentScrollY;
    private int tableScrollY;
    private Font labelFont;
    private Font tableFont;
    private Font generalFont;
    private Font smallLabelFont;
    private int studentBotY;
    private int tableBotY;
    private boolean onPanel;
    private boolean tableRearrangeMode;
    private double tableRadius;
    private double walkingRadius;
    private boolean prevPressed;
    private boolean justReleased;
    private boolean clickOnPanel;
    private int saveCount;
    private boolean start;
    private double textShift;
    private ArrayList<File> saveState;
    private int ctrlZNum;
    private boolean switchMode;

    FloorPanel() {
        this.WIDTH = (double)Toolkit.getDefaultToolkit().getScreenSize().width;
        this.HEIGHT = (double)(Toolkit.getDefaultToolkit().getScreenSize().height - 50);
        this.LEFT_PANEL_PERCENT = 0.2D;
        this.TOP_PANEL_PERCENT = 0.03333333333333333D;
        this.DIVISION_PANEL_PERCENT_TOP = 0.375D;
        this.DIVISION_PANEL_PERCENT_MID = 0.375D;
        this.DIVISION_PANEL_PERCENT_BOT = 0.25D;
        this.BORDER_WIDTH = 1;
        this.TEXT_HEIGHT = 30;
        this.TEXT_LEFT_SHIFT = 20;
        this.drawTables = new ArrayList();
        this.displayStudentOrder = new ArrayList();
        this.displayTableOrder = new ArrayList();
        this.x = 0;
        this.y = 0;
        this.roomHeight = 1000.0D;
        this.roomWidth = 1000.0D;
        this.hCircle = 0;
        this.wCircle = 0;
        this.wTwoCircle = 0;
        this.maxTableCapacity = 0;
        this.scalingWidth = 1.0D;
        this.scalingHeight = 1.0D;
        this.offset = false;
        this.xCircleCount = 0;
        this.myMouseAdapter = new CustomMouseAdapter();
        this.myKeyListener = new CustomKeyListener();
        this.justPressed = true;
        this.justPanelPressed = true;
        this.studentScrollY = 0;
        this.tableScrollY = 0;
        this.studentBotY = 0;
        this.tableBotY = 0;
        this.onPanel = true;
        this.tableRearrangeMode = false;
        this.tableRadius = 20.0D;
        this.walkingRadius = 20.0D;
        this.saveCount = 0;
        this.start = true;
        this.textShift = (double)this.TEXT_HEIGHT;
        this.saveState = new ArrayList();
        this.ctrlZNum = 0;
        this.switchMode = false;

        for(int i = 0; i < 10; ++i) {
            this.saveState.add(new File(i + "save.txt"));
        }

        this.setDoubleBuffered(true);
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        this.addMouseListener(this.myMouseAdapter);
        this.addMouseWheelListener(this.myMouseAdapter);
        this.addMouseMotionListener(this.myMouseAdapter);
        this.addKeyListener(this.myKeyListener);
        this.setBackground(new Color(37, 37, 37));

        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(0, new File("Kiona-Bold.ttf")));
            ge.registerFont(Font.createFont(0, new File("Brandon_bld.ttf")));
            ge.registerFont(Font.createFont(0, new File("Brandon_reg.ttf")));
        } catch (FontFormatException | IOException var2) {
            System.out.print("Font not available, tell Will to fix");
        }

        this.labelFont = new Font("Brandon Grotesque Bold", 0, 16);
        this.smallLabelFont = new Font("Brandon Grotesque Bold", 0, 8);
        this.generalFont = new Font("Brandon Grotesque Regular", 0, 14);
        this.tableFont = new Font("Kiona Bold", 0, 20);
    }

    public void paintComponent(Graphics g) {
        if (this.start) {
            this.clearAllStates();
            this.saveImage(0);
            this.start = false;
        }

        Graphics2D gTwo = (Graphics2D)g.create();
        gTwo.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gTwo.setFont(this.labelFont);
        gTwo.setColor(Color.WHITE);
        super.paintComponent(g);
        this.requestFocusInWindow();
        int tempScroll = this.myMouseAdapter.scroller();
        int[] mouseXy = new int[2];
        boolean mousePressed = this.myMouseAdapter.getPressed();
        mouseXy[0] = this.myMouseAdapter.getMouseXy()[0];
        mouseXy[1] = this.myMouseAdapter.getMouseXy()[1];
        int[] mouseClickXy = new int[]{this.myMouseAdapter.getMouseClickXy()[0], this.myMouseAdapter.getMouseClickXy()[1]};
        boolean ctrlZ = this.myKeyListener.getReversed();
        boolean ctrlY = this.myKeyListener.getForward();
        if ((double)mouseXy[0] > this.WIDTH * this.LEFT_PANEL_PERCENT && (double)mouseXy[1] > this.HEIGHT * this.TOP_PANEL_PERCENT) {
            this.onPanel = false;
        } else {
            this.onPanel = true;
        }

        if ((double)mouseClickXy[0] > this.WIDTH * this.LEFT_PANEL_PERCENT && (double)mouseClickXy[1] > this.HEIGHT * this.TOP_PANEL_PERCENT) {
            this.clickOnPanel = false;
        } else {
            this.clickOnPanel = true;
        }

        if (this.justReleased && !this.checkConstant()) {
            this.save();
            this.ctrlZNum = 0;
        }

        if (mousePressed && !this.onPanel) {
            if (!this.switchMode && !this.tableRearrangeMode && !this.clickOnPanel) {
                this.xAdjust = -(this.myMouseAdapter.getMouseClickXy()[0] - this.myMouseAdapter.getMouseDragXy()[0]);
                this.yAdjust = -(this.myMouseAdapter.getMouseClickXy()[1] - this.myMouseAdapter.getMouseDragXy()[1]);
            }

            this.justPressed = true;
        }

        if (!mousePressed && this.prevPressed) {
            this.justReleased = true;
        } else {
            this.justReleased = false;
        }

        if (mousePressed) {
            this.justPanelPressed = true;
            this.prevPressed = true;
        } else {
            this.prevPressed = false;
        }

        if (ctrlZ) {
            this.controlZ();
        }

        if (ctrlY) {
            this.controlY();
        }

        if (mousePressed && this.onPanel && this.justPressed && !this.switchMode && !this.tableRearrangeMode) {
            this.xPerm += this.xAdjust;
            this.yPerm += this.yAdjust;
            this.yAdjust = 0;
            this.xAdjust = 0;
        }

        if (this.onPanel && !this.switchMode && !this.tableRearrangeMode) {
            this.xAdjust = 0;
            this.yAdjust = 0;
        }

        if (this.justPanelPressed && !mousePressed) {
            this.buttonRefresh = true;
            if (!this.switchMode && !this.tableRearrangeMode) {
                this.xPerm += this.xAdjust;
                this.yPerm += this.yAdjust;
                this.xAdjust = 0;
                this.yAdjust = 0;
            }

            this.justPressed = false;
            this.clickRefresh = true;
        }

        double xFrac;
        double yFrac;
        if (mousePressed && !this.onPanel) {
            xFrac = (double)(mouseXy[0] - (this.panX + this.xPerm + this.xAdjust)) / (this.roomWidth * this.scaling);
            yFrac = (double)(mouseXy[1] - (this.panY + this.yPerm + this.yAdjust)) / (this.roomHeight * this.scaling);
        } else {
            xFrac = (double)(mouseXy[0] - (this.panX + this.xPerm)) / (this.roomWidth * this.scaling);
            yFrac = (double)(mouseXy[1] - (this.panY + this.yPerm)) / (this.roomHeight * this.scaling);
        }

        if (tempScroll != 0) {
            if (!this.onPanel) {
                if (tempScroll > 0) {
                    this.scaling *= 1.1D;
                } else if (tempScroll < 0 && this.scaling * this.roomWidth > 50.0D && this.scaling * this.roomHeight > 50.0D) {
                    this.scaling = this.scaling * 10.0D / 11.0D;
                }
            } else if ((double)mouseXy[1] >= this.TOP_PANEL_PERCENT * this.HEIGHT && (double)mouseXy[1] <= this.HEIGHT * this.TOP_PANEL_PERCENT + this.DIVISION_PANEL_PERCENT_TOP * this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT)) {
                if (tempScroll > 0) {
                    if (this.studentScrollY < 0) {
                        this.studentScrollY += this.TEXT_HEIGHT;
                    }
                } else if (tempScroll < 0 && (double)this.studentBotY >= this.HEIGHT * this.TOP_PANEL_PERCENT + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_TOP) {
                    this.studentScrollY -= this.TEXT_HEIGHT;
                }
            } else if ((double)mouseXy[1] >= (1.0D - this.TOP_PANEL_PERCENT) * this.HEIGHT * this.DIVISION_PANEL_PERCENT_TOP + this.TOP_PANEL_PERCENT * this.HEIGHT && (double)mouseXy[1] <= (1.0D - this.TOP_PANEL_PERCENT) * this.HEIGHT * (this.DIVISION_PANEL_PERCENT_MID + this.DIVISION_PANEL_PERCENT_TOP) + this.TOP_PANEL_PERCENT * this.HEIGHT) {
                if (tempScroll > 0) {
                    if (this.tableScrollY < 0) {
                        this.tableScrollY += this.TEXT_HEIGHT;
                    }
                } else if (tempScroll < 0 && (double)this.tableBotY >= (1.0D - this.TOP_PANEL_PERCENT) * this.HEIGHT * (this.DIVISION_PANEL_PERCENT_MID + this.DIVISION_PANEL_PERCENT_TOP) + this.TOP_PANEL_PERCENT * this.HEIGHT) {
                    this.tableScrollY -= this.TEXT_HEIGHT;
                }
            }

            if (!this.onPanel) {
                if (mousePressed) {
                    this.panX = (int)(-((double)(-mouseXy[0] + this.xPerm + this.xAdjust) + xFrac * this.scaling * this.roomWidth));
                    this.panY = (int)(-((double)(-mouseXy[1] + this.yPerm + this.yAdjust) + yFrac * this.scaling * this.roomHeight));
                } else {
                    this.panX = (int)(-((double)(-mouseXy[0] + this.xPerm) + xFrac * this.scaling * this.roomWidth));
                    this.panY = (int)(-((double)(-mouseXy[1] + this.yPerm) + yFrac * this.scaling * this.roomHeight));
                }
            }
        }

        gTwo.setColor(Color.WHITE);
        if (mousePressed && !this.onPanel) {
            gTwo.drawRect(this.panX + this.xPerm + this.xAdjust, this.panY + this.yPerm + this.yAdjust, (int)(this.roomWidth * this.scaling), (int)(this.roomHeight * this.scaling));
        } else {
            gTwo.drawRect(this.panX + this.xPerm, this.panY + this.yPerm, (int)(this.roomWidth * this.scaling), (int)(this.roomHeight * this.scaling));
        }

        int i;
        int tableCenterY;
        double tempAngle;
        int t;
        boolean cutUp;
        int j;
        int centerY;
        int i;
        for(i = 0; i < this.drawTables.size(); ++i) {
            int tableCenterX;
            if (mousePressed && !this.onPanel) {
                tableCenterX = this.panX + this.xPerm + this.xAdjust + (int)((this.tableRadius + this.walkingRadius + ((DrawnTable)this.drawTables.get(i)).getXPercent() * this.roomWidth) * this.scaling);
                tableCenterY = this.panY + this.yPerm + this.yAdjust + (int)((this.tableRadius + this.walkingRadius + ((DrawnTable)this.drawTables.get(i)).getYPercent() * this.roomHeight) * this.scaling);
            } else {
                tableCenterX = this.panX + this.xPerm + (int)((this.tableRadius + this.walkingRadius + ((DrawnTable)this.drawTables.get(i)).getXPercent() * this.roomWidth) * this.scaling);
                tableCenterY = this.panY + this.yPerm + (int)((this.tableRadius + this.walkingRadius + ((DrawnTable)this.drawTables.get(i)).getYPercent() * this.roomHeight) * this.scaling);
            }

            double currentAngle;
            if (this.justReleased && !this.onPanel && this.tableRearrangeMode && ((DrawnTable)this.drawTables.get(i)).getSelected() && Math.pow((double)(mouseClickXy[0] - tableCenterX), 2.0D) + Math.pow((double)(mouseClickXy[1] - tableCenterY), 2.0D) <= Math.pow(this.tableRadius * this.scaling, 2.0D)) {
                tempAngle = (double)(mouseXy[0] - mouseClickXy[0] + this.panX + this.xPerm + this.xAdjust + (int)(((DrawnTable)this.drawTables.get(i)).getXPercent() * this.roomWidth * this.scaling) - (this.panX + this.xPerm));
                currentAngle = (double)(mouseXy[1] - mouseClickXy[1] + this.panY + this.yPerm + this.yAdjust + (int)(((DrawnTable)this.drawTables.get(i)).getYPercent() * this.roomHeight * this.scaling) - (this.panY + this.yPerm));
                if (tempAngle + (this.tableRadius + this.walkingRadius) * 2.0D * this.scaling < this.roomWidth * this.scaling && tempAngle > 0.0D && currentAngle + (this.tableRadius + this.walkingRadius) * 2.0D * this.scaling < this.roomHeight * this.scaling && currentAngle > 0.0D) {
                    cutUp = false;

                    for(j = 0; j < this.drawTables.size(); ++j) {
                        if (i != j) {
                            centerY = this.panX + this.xPerm + (int)((this.tableRadius + this.walkingRadius + ((DrawnTable)this.drawTables.get(j)).getXPercent() * this.roomWidth) * this.scaling);
                            int secondCenterY = this.panY + this.yPerm + (int)((this.tableRadius + this.walkingRadius + ((DrawnTable)this.drawTables.get(j)).getYPercent() * this.roomHeight) * this.scaling);
                            if (Math.hypot((double)(centerY - (tableCenterX + (mouseXy[0] - mouseClickXy[0]))), (double)(secondCenterY - (tableCenterY + (mouseXy[1] - mouseClickXy[1])))) < (this.tableRadius + this.walkingRadius) * 2.0D * this.scaling) {
                                cutUp = true;
                            }
                        }
                    }

                    if (!cutUp) {
                        ((DrawnTable)this.drawTables.get(i)).setXPercent(tempAngle / (this.roomWidth * this.scaling));
                        ((DrawnTable)this.drawTables.get(i)).setYPercent(currentAngle / (this.roomHeight * this.scaling));
                    }
                }
            }

            gTwo.setColor(Color.LIGHT_GRAY);
            if (mousePressed && !this.onPanel) {
                if (this.tableRearrangeMode && ((DrawnTable)this.drawTables.get(i)).getSelected() && Math.pow((double)(mouseClickXy[0] - tableCenterX), 2.0D) + Math.pow((double)(mouseClickXy[1] - tableCenterY), 2.0D) <= Math.pow(this.tableRadius * this.scaling, 2.0D)) {
                    ((DrawnTable)this.drawTables.get(i)).drawWalking(gTwo, mouseXy[0] - mouseClickXy[0] + this.panX + this.xPerm + this.xAdjust + (int)(((DrawnTable)this.drawTables.get(i)).getXPercent() * this.roomWidth * this.scaling), mouseXy[1] - mouseClickXy[1] + this.panY + this.yPerm + this.yAdjust + (int)(((DrawnTable)this.drawTables.get(i)).getYPercent() * this.roomHeight * this.scaling), (int)((this.tableRadius + this.walkingRadius) * 2.0D * this.scaling), (int)((this.tableRadius + this.walkingRadius) * 2.0D * this.scaling));
                } else {
                    ((DrawnTable)this.drawTables.get(i)).drawWalking(gTwo, this.panX + this.xPerm + this.xAdjust + (int)(((DrawnTable)this.drawTables.get(i)).getXPercent() * this.roomWidth * this.scaling), this.panY + this.yPerm + this.yAdjust + (int)(((DrawnTable)this.drawTables.get(i)).getYPercent() * this.roomHeight * this.scaling), (int)((this.tableRadius + this.walkingRadius) * 2.0D * this.scaling), (int)((this.tableRadius + this.walkingRadius) * 2.0D * this.scaling));
                }
            } else {
                ((DrawnTable)this.drawTables.get(i)).drawWalking(gTwo, this.panX + this.xPerm + (int)(((DrawnTable)this.drawTables.get(i)).getXPercent() * this.roomWidth * this.scaling), this.panY + this.yPerm + (int)(((DrawnTable)this.drawTables.get(i)).getYPercent() * this.roomHeight * this.scaling), (int)((this.tableRadius + this.walkingRadius) * 2.0D * this.scaling), (int)((this.tableRadius + this.walkingRadius) * 2.0D * this.scaling));
            }

            int tempCentX;
            if (mousePressed && !this.onPanel) {
                if (this.tableRearrangeMode && ((DrawnTable)this.drawTables.get(i)).getSelected() && Math.pow((double)(mouseClickXy[0] - tableCenterX), 2.0D) + Math.pow((double)(mouseClickXy[1] - tableCenterY), 2.0D) <= Math.pow(this.tableRadius * this.scaling, 2.0D)) {
                    ((DrawnTable)this.drawTables.get(i)).fillTable(gTwo, mouseXy[0] - mouseClickXy[0] + this.panX + this.xPerm + this.xAdjust + (int)((this.walkingRadius + ((DrawnTable)this.drawTables.get(i)).getXPercent() * this.roomWidth) * this.scaling), mouseXy[1] - mouseClickXy[1] + this.panY + this.yPerm + this.yAdjust + (int)(((DrawnTable)this.drawTables.get(i)).getYPercent() * this.roomHeight * this.scaling) + (int)(this.walkingRadius * this.scaling), (int)(this.tableRadius * 2.0D * this.scaling), (int)(this.tableRadius * 2.0D * this.scaling));
                } else {
                    ((DrawnTable)this.drawTables.get(i)).fillTable(gTwo, this.panX + this.xPerm + this.xAdjust + (int)((this.walkingRadius + ((DrawnTable)this.drawTables.get(i)).getXPercent() * this.roomWidth) * this.scaling), this.panY + this.yPerm + this.yAdjust + (int)(((DrawnTable)this.drawTables.get(i)).getYPercent() * this.roomHeight * this.scaling) + (int)(this.walkingRadius * this.scaling), (int)(this.tableRadius * 2.0D * this.scaling), (int)(this.tableRadius * 2.0D * this.scaling));
                }

                gTwo.setColor(Color.BLACK);
                tempCentX = this.xAdjust + (int)(this.tableRadius * this.scaling) + this.panX + this.xPerm + (int)((this.walkingRadius + ((DrawnTable)this.drawTables.get(i)).getXPercent() * this.roomWidth) * this.scaling);
                t = this.yAdjust + (int)(this.tableRadius * this.scaling) + this.panY + this.yPerm + (int)(((DrawnTable)this.drawTables.get(i)).getYPercent() * this.roomHeight * this.scaling) + (int)(this.walkingRadius * this.scaling);
                gTwo.setFont(this.tableFont);
                if (((DrawnTable)this.drawTables.get(i)).getSelected()) {
                    if (this.tableRearrangeMode && ((DrawnTable)this.drawTables.get(i)).getSelected() && Math.pow((double)(mouseClickXy[0] - tableCenterX), 2.0D) + Math.pow((double)(mouseClickXy[1] - tableCenterY), 2.0D) <= Math.pow(this.tableRadius * this.scaling, 2.0D)) {
                        gTwo.drawString(i + 1 + "", mouseXy[0] - mouseClickXy[0] + tempCentX - (int)(1.0D * (double)gTwo.getFontMetrics().stringWidth(i + 1 + "") / 2.0D), mouseXy[1] - mouseClickXy[1] + t + 7);
                    } else {
                        gTwo.drawString(i + 1 + "", tempCentX - (int)(1.0D * (double)gTwo.getFontMetrics().stringWidth(i + 1 + "") / 2.0D), t + 7);
                    }
                }

                gTwo.setFont(this.labelFont);
            } else {
                ((DrawnTable)this.drawTables.get(i)).fillTable(gTwo, this.panX + this.xPerm + (int)((this.walkingRadius + ((DrawnTable)this.drawTables.get(i)).getXPercent() * this.roomWidth) * this.scaling), this.panY + this.yPerm + (int)(((DrawnTable)this.drawTables.get(i)).getYPercent() * this.roomHeight * this.scaling) + (int)(this.walkingRadius * this.scaling), (int)(this.tableRadius * 2.0D * this.scaling), (int)(this.tableRadius * 2.0D * this.scaling));
                gTwo.setColor(Color.BLACK);
                tempCentX = (int)(this.tableRadius * this.scaling) + this.panX + this.xPerm + (int)((this.walkingRadius + ((DrawnTable)this.drawTables.get(i)).getXPercent() * this.roomWidth) * this.scaling);
                t = (int)(this.tableRadius * this.scaling) + this.panY + this.yPerm + (int)(((DrawnTable)this.drawTables.get(i)).getYPercent() * this.roomHeight * this.scaling) + (int)(this.walkingRadius * this.scaling);
                gTwo.setFont(this.tableFont);
                if (((DrawnTable)this.drawTables.get(i)).getSelected()) {
                    gTwo.drawString(i + 1 + "", (int)(1.0D * (double)(-gTwo.getFontMetrics().stringWidth(i + 1 + "")) / 2.0D) + tempCentX, t + 7);
                }

                gTwo.setFont(this.labelFont);
            }

            tempAngle = 6.283185307179586D / (double)((DrawnTable)this.drawTables.get(i)).getStudents().size();
            currentAngle = 0.0D;

            for(i = 0; i < ((DrawnTable)this.drawTables.get(i)).getStudents().size(); ++i) {
                if (mousePressed && !this.onPanel) {
                    if (this.tableRearrangeMode && ((DrawnTable)this.drawTables.get(i)).getSelected() && Math.pow((double)(mouseClickXy[0] - tableCenterX), 2.0D) + Math.pow((double)(mouseClickXy[1] - tableCenterY), 2.0D) <= Math.pow(this.tableRadius * this.scaling, 2.0D)) {
                        ((DrawnStudent)((DrawnTable)this.drawTables.get(i)).getStudents().get(i)).fillStudent(gTwo, mouseXy[0] - mouseClickXy[0] + this.panX + this.xPerm + this.xAdjust + (int)((this.walkingRadius + this.tableRadius - this.tableRadius / 6.0D + Math.sin(currentAngle) * this.tableRadius + ((DrawnTable)this.drawTables.get(i)).getXPercent() * this.roomWidth) * this.scaling), mouseXy[1] - mouseClickXy[1] + this.panY + this.yPerm + this.yAdjust + (int)(((DrawnTable)this.drawTables.get(i)).getYPercent() * this.roomHeight * this.scaling) + (int)((this.walkingRadius + this.tableRadius - this.tableRadius / 6.0D + Math.cos(currentAngle) * this.tableRadius) * this.scaling), (int)(this.tableRadius / 3.0D * this.scaling), (int)(this.tableRadius / 3.0D * this.scaling));
                    } else {
                        ((DrawnStudent)((DrawnTable)this.drawTables.get(i)).getStudents().get(i)).fillStudent(gTwo, this.panX + this.xPerm + this.xAdjust + (int)((this.walkingRadius + this.tableRadius - this.tableRadius / 6.0D + Math.sin(currentAngle) * this.tableRadius + ((DrawnTable)this.drawTables.get(i)).getXPercent() * this.roomWidth) * this.scaling), this.panY + this.yPerm + this.yAdjust + (int)(((DrawnTable)this.drawTables.get(i)).getYPercent() * this.roomHeight * this.scaling) + (int)((this.walkingRadius + this.tableRadius - this.tableRadius / 6.0D + Math.cos(currentAngle) * this.tableRadius) * this.scaling), (int)(this.tableRadius / 3.0D * this.scaling), (int)(this.tableRadius / 3.0D * this.scaling));
                    }

                    if (((DrawnStudent)((DrawnTable)this.drawTables.get(i)).getStudents().get(i)).getSelected()) {
                        gTwo.setFont(this.tableFont);
                        gTwo.setColor(Color.BLACK);
                        if (this.tableRearrangeMode && ((DrawnTable)this.drawTables.get(i)).getSelected() && Math.pow((double)(mouseClickXy[0] - tableCenterX), 2.0D) + Math.pow((double)(mouseClickXy[1] - tableCenterY), 2.0D) <= Math.pow(this.tableRadius * this.scaling, 2.0D)) {
                            gTwo.drawString(((DrawnStudent)((DrawnTable)this.drawTables.get(i)).getStudents().get(i)).getPos(), mouseXy[0] - mouseClickXy[0] + (int)(this.tableRadius / 6.0D * this.scaling) - (int)(1.0D * (double)gTwo.getFontMetrics().stringWidth(((DrawnStudent)((DrawnTable)this.drawTables.get(i)).getStudents().get(i)).getPos()) / 2.0D) + this.panX + this.xPerm + this.xAdjust + (int)((this.walkingRadius + this.tableRadius - this.tableRadius / 6.0D + Math.sin(currentAngle) * this.tableRadius + ((DrawnTable)this.drawTables.get(i)).getXPercent() * this.roomWidth) * this.scaling), mouseXy[1] - mouseClickXy[1] + this.panY + this.yPerm + this.yAdjust + (int)(((DrawnTable)this.drawTables.get(i)).getYPercent() * this.roomHeight * this.scaling) + (int)((this.walkingRadius + this.tableRadius - this.tableRadius / 6.0D + Math.cos(currentAngle) * this.tableRadius) * this.scaling));
                        } else {
                            gTwo.drawString(((DrawnStudent)((DrawnTable)this.drawTables.get(i)).getStudents().get(i)).getPos(), (int)(this.tableRadius / 6.0D * this.scaling) - (int)(1.0D * (double)gTwo.getFontMetrics().stringWidth(((DrawnStudent)((DrawnTable)this.drawTables.get(i)).getStudents().get(i)).getPos()) / 2.0D) + this.panX + this.xPerm + this.xAdjust + (int)((this.walkingRadius + this.tableRadius - this.tableRadius / 6.0D + Math.sin(currentAngle) * this.tableRadius + ((DrawnTable)this.drawTables.get(i)).getXPercent() * this.roomWidth) * this.scaling), this.panY + this.yPerm + this.yAdjust + (int)(((DrawnTable)this.drawTables.get(i)).getYPercent() * this.roomHeight * this.scaling) + (int)((this.walkingRadius + this.tableRadius - this.tableRadius / 6.0D + Math.cos(currentAngle) * this.tableRadius) * this.scaling));
                        }
                    }
                } else {
                    ((DrawnStudent)((DrawnTable)this.drawTables.get(i)).getStudents().get(i)).fillStudent(gTwo, this.panX + this.xPerm + (int)((this.walkingRadius + this.tableRadius - this.tableRadius / 6.0D + Math.sin(currentAngle) * this.tableRadius + ((DrawnTable)this.drawTables.get(i)).getXPercent() * this.roomWidth) * this.scaling), this.panY + this.yPerm + (int)(((DrawnTable)this.drawTables.get(i)).getYPercent() * this.roomHeight * this.scaling) + (int)((this.walkingRadius + this.tableRadius - this.tableRadius / 6.0D + Math.cos(currentAngle) * this.tableRadius) * this.scaling), (int)(this.tableRadius / 3.0D * this.scaling), (int)(this.tableRadius / 3.0D * this.scaling));
                    if (((DrawnStudent)((DrawnTable)this.drawTables.get(i)).getStudents().get(i)).getSelected()) {
                        gTwo.setFont(this.tableFont);
                        gTwo.setColor(Color.BLACK);
                        gTwo.drawString(((DrawnStudent)((DrawnTable)this.drawTables.get(i)).getStudents().get(i)).getPos(), (int)(this.tableRadius / 6.0D * this.scaling) - (int)(1.0D * (double)gTwo.getFontMetrics().stringWidth(((DrawnStudent)((DrawnTable)this.drawTables.get(i)).getStudents().get(i)).getPos()) / 2.0D) + this.panX + this.xPerm + (int)((this.walkingRadius + this.tableRadius - this.tableRadius / 6.0D + Math.sin(currentAngle) * this.tableRadius + ((DrawnTable)this.drawTables.get(i)).getXPercent() * this.roomWidth) * this.scaling), this.panY + this.yPerm + (int)(((DrawnTable)this.drawTables.get(i)).getYPercent() * this.roomHeight * this.scaling) + (int)((this.walkingRadius + this.tableRadius - this.tableRadius / 6.0D + Math.cos(currentAngle) * this.tableRadius) * this.scaling));
                    }
                }

                gTwo.setFont(this.labelFont);
                j = this.panX + this.xPerm + this.xAdjust + (int)((this.walkingRadius + this.tableRadius - this.tableRadius / 6.0D + Math.sin(currentAngle) * this.tableRadius + ((DrawnTable)this.drawTables.get(i)).getXPercent() * this.roomWidth) * this.scaling + this.tableRadius / 3.0D * this.scaling / 2.0D);
                centerY = this.panY + this.yPerm + this.yAdjust + (int)((this.walkingRadius + this.tableRadius - this.tableRadius / 6.0D + Math.cos(currentAngle) * this.tableRadius + ((DrawnTable)this.drawTables.get(i)).getYPercent() * this.roomHeight) * this.scaling + this.tableRadius / 3.0D * this.scaling / 2.0D);
                if (this.clickRefresh && mousePressed && !this.onPanel && Math.pow((double)(mouseClickXy[0] - j), 2.0D) + Math.pow((double)(mouseClickXy[1] - centerY), 2.0D) <= Math.pow(this.tableRadius / 3.0D * this.scaling / 2.0D, 2.0D)) {
                    ((DrawnStudent)((DrawnTable)this.drawTables.get(i)).getStudents().get(i)).setSelected();
                    this.clickRefresh = false;
                }

                currentAngle += tempAngle;
            }

            if (this.clickRefresh && mousePressed && !this.onPanel && Math.pow((double)(mouseXy[0] - tableCenterX), 2.0D) + Math.pow((double)(mouseXy[1] - tableCenterY), 2.0D) <= Math.pow(this.tableRadius * this.scaling, 2.0D) && Math.pow((double)(mouseClickXy[0] - tableCenterX), 2.0D) + Math.pow((double)(mouseClickXy[1] - tableCenterY), 2.0D) <= Math.pow(this.tableRadius * this.scaling, 2.0D)) {
                if (this.tableRearrangeMode) {
                    for(i = 0; i < this.drawTables.size(); ++i) {
                        if (((DrawnTable)this.drawTables.get(i)).getSelected()) {
                            ((DrawnTable)this.drawTables.get(i)).setSelected();
                        }
                    }
                }

                ((DrawnTable)this.drawTables.get(i)).setSelected();
                this.clickRefresh = false;
            }
        }

        gTwo.setColor(new Color(41, 41, 41));
        gTwo.fillRect(0, 0, (int)(this.WIDTH * this.LEFT_PANEL_PERCENT), (int)this.HEIGHT);
        gTwo.setFont(this.generalFont);
        gTwo.setColor(new Color(41, 41, 41));
        gTwo.fillRect(0, (int)(this.HEIGHT * this.TOP_PANEL_PERCENT), (int)(this.WIDTH * this.LEFT_PANEL_PERCENT), (int)(this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_TOP));
        gTwo.setColor(new Color(59, 59, 59));
        gTwo.fillRect(this.BORDER_WIDTH, this.BORDER_WIDTH + (int)(this.HEIGHT * this.TOP_PANEL_PERCENT), (int)(this.WIDTH * this.LEFT_PANEL_PERCENT) - 2 * this.BORDER_WIDTH, (int)(this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_TOP) - 2 * this.BORDER_WIDTH);
        gTwo.setColor(Color.WHITE);
        this.textShift = (double)this.TEXT_HEIGHT;

        String studentString;
        for(i = 0; i < this.displayStudentOrder.size(); ++i) {
            studentString = "";

            for(tableCenterY = 0; tableCenterY < ((DrawnStudent)this.displayStudentOrder.get(i)).getDietaryRestrictions().size(); ++tableCenterY) {
                if (tableCenterY != ((DrawnStudent)this.displayStudentOrder.get(i)).getDietaryRestrictions().size() - 1) {
                    studentString = studentString + (String)((DrawnStudent)this.displayStudentOrder.get(i)).getDietaryRestrictions().get(tableCenterY) + ", ";
                } else {
                    studentString = studentString + (String)((DrawnStudent)this.displayStudentOrder.get(i)).getDietaryRestrictions().get(tableCenterY);
                }
            }

            String overallString;
            if (((DrawnStudent)this.displayStudentOrder.get(i)).getDietaryRestrictions().size() != 0) {
                overallString = ((DrawnStudent)this.displayStudentOrder.get(i)).getPos() + " " + ((DrawnStudent)this.displayStudentOrder.get(i)).getName() + ", " + ((DrawnStudent)this.displayStudentOrder.get(i)).getStudentNumber() + ". Restrictions: " + studentString;
            } else {
                overallString = ((DrawnStudent)this.displayStudentOrder.get(i)).getPos() + " " + ((DrawnStudent)this.displayStudentOrder.get(i)).getName() + ", " + ((DrawnStudent)this.displayStudentOrder.get(i)).getStudentNumber();
            }

            tempAngle = (double)gTwo.getFontMetrics().stringWidth(overallString);
            ArrayList<String> sentences = new ArrayList();
            String newSentence = "";
            i = 0;
            boolean cutUp = false;
            if (tempAngle < this.WIDTH * this.LEFT_PANEL_PERCENT - 20.0D - (double)this.TEXT_LEFT_SHIFT) {
                cutUp = true;
            }

            sentences.add(overallString);

            while(!cutUp) {
                while(tempAngle > this.WIDTH * this.LEFT_PANEL_PERCENT - 20.0D - (double)this.TEXT_LEFT_SHIFT) {
                    newSentence = ((String)sentences.get(i)).substring(((String)sentences.get(i)).length() - 1) + newSentence;
                    sentences.set(i, ((String)sentences.get(i)).substring(0, ((String)sentences.get(i)).length() - 1));
                    tempAngle = (double)gTwo.getFontMetrics().stringWidth((String)sentences.get(i) + "-");
                }

                sentences.add(newSentence);
                ++i;
                tempAngle = (double)gTwo.getFontMetrics().stringWidth(newSentence);
                newSentence = "";
                if (tempAngle <= this.WIDTH * this.LEFT_PANEL_PERCENT - 20.0D - (double)this.TEXT_LEFT_SHIFT) {
                    cutUp = true;
                }
            }

            for(centerY = 0; centerY < sentences.size(); ++centerY) {
                if (this.studentScrollY - this.BORDER_WIDTH + (int)(this.HEIGHT * this.TOP_PANEL_PERCENT + (double)(this.TEXT_HEIGHT * 2) + this.textShift) >= -this.BORDER_WIDTH + (int)(this.HEIGHT * this.TOP_PANEL_PERCENT) + this.TEXT_HEIGHT + gTwo.getFontMetrics(this.generalFont).getHeight()) {
                    sentences.set(centerY, ((String)sentences.get(centerY)).trim());
                    if (!((String)sentences.get(centerY)).substring(((String)sentences.get(centerY)).length() - 1).equals(",") && centerY != sentences.size() - 1) {
                        sentences.set(centerY, (String)sentences.get(centerY) + "-");
                    }

                    gTwo.drawString((String)sentences.set(centerY, ((String)sentences.get(centerY)).trim()), this.TEXT_LEFT_SHIFT, this.studentScrollY - this.BORDER_WIDTH + (int)(this.HEIGHT * this.TOP_PANEL_PERCENT + (double)this.TEXT_HEIGHT + this.textShift));
                }

                if (i == this.displayStudentOrder.size() - 1 && centerY == sentences.size() - 1) {
                    this.studentBotY = this.studentScrollY - this.BORDER_WIDTH + (int)(this.HEIGHT * this.TOP_PANEL_PERCENT + (double)this.TEXT_HEIGHT + this.textShift);
                }

                this.textShift += (double)this.TEXT_HEIGHT;
            }

            this.textShift += (double)this.TEXT_HEIGHT;
        }

        gTwo.setFont(this.labelFont);
        gTwo.setColor(new Color(59, 59, 59));
        gTwo.fillRect(0, (int)(this.HEIGHT * this.TOP_PANEL_PERCENT), (int)(this.WIDTH * this.LEFT_PANEL_PERCENT), -((int)(this.HEIGHT * this.TOP_PANEL_PERCENT)) + -this.BORDER_WIDTH + (int)(this.HEIGHT * this.TOP_PANEL_PERCENT) + this.TEXT_HEIGHT + 3);
        gTwo.setColor(Color.WHITE);
        gTwo.drawString("STUDENTS", this.TEXT_LEFT_SHIFT, -this.BORDER_WIDTH + (int)(this.HEIGHT * this.TOP_PANEL_PERCENT) + this.TEXT_HEIGHT);
        gTwo.setFont(this.generalFont);
        gTwo.setColor(new Color(41, 41, 41));
        gTwo.fillRect(0, (int)(this.HEIGHT * this.TOP_PANEL_PERCENT + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_TOP), (int)(this.WIDTH * this.LEFT_PANEL_PERCENT), (int)(this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_MID));
        gTwo.setColor(new Color(59, 59, 59));
        gTwo.fillRect(this.BORDER_WIDTH, this.BORDER_WIDTH + (int)(this.HEIGHT * this.TOP_PANEL_PERCENT + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_TOP), (int)(this.WIDTH * this.LEFT_PANEL_PERCENT) - 2 * this.BORDER_WIDTH, (int)(this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_MID) - 2 * this.BORDER_WIDTH);
        gTwo.setColor(Color.WHITE);
        this.textShift = (double)this.TEXT_HEIGHT;

        for(i = 0; i < this.displayTableOrder.size(); ++i) {
            studentString = "";

            for(tableCenterY = 0; tableCenterY < ((DrawnTable)this.displayTableOrder.get(i)).getStudents().size(); ++tableCenterY) {
                if (tableCenterY != 0) {
                    studentString = studentString + ", " + ((DrawnStudent)((DrawnTable)this.displayTableOrder.get(i)).getStudents().get(tableCenterY)).getStudentNumber();
                } else {
                    studentString = ((DrawnStudent)((DrawnTable)this.displayTableOrder.get(i)).getStudents().get(tableCenterY)).getStudentNumber();
                }
            }

            if ((double)(this.tableScrollY - this.BORDER_WIDTH + (int)(this.textShift + this.HEIGHT * this.TOP_PANEL_PERCENT + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_TOP) + this.TEXT_HEIGHT * 2) >= (double)(-this.BORDER_WIDTH + (int)(this.HEIGHT * this.TOP_PANEL_PERCENT) + this.TEXT_HEIGHT + gTwo.getFontMetrics(this.generalFont).getHeight()) + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_TOP) {
                gTwo.drawString("Table " + (this.drawTables.indexOf(this.displayTableOrder.get(i)) + 1) + " Students", this.TEXT_LEFT_SHIFT, this.tableScrollY - this.BORDER_WIDTH + (int)(this.textShift + this.HEIGHT * this.TOP_PANEL_PERCENT + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_TOP) + this.TEXT_HEIGHT);
            }

            double pixelLength = (double)gTwo.getFontMetrics().stringWidth(studentString);
            ArrayList<String> sentences = new ArrayList();
            String newSentence = "";
            int counter = 0;
            cutUp = false;
            if (pixelLength < this.WIDTH * this.LEFT_PANEL_PERCENT - 20.0D - (double)this.TEXT_LEFT_SHIFT) {
                cutUp = true;
            }

            sentences.add(studentString);

            while(!cutUp) {
                while(pixelLength > this.WIDTH * this.LEFT_PANEL_PERCENT - 20.0D - (double)this.TEXT_LEFT_SHIFT) {
                    newSentence = ((String)sentences.get(counter)).substring(((String)sentences.get(counter)).length() - 1) + newSentence;
                    sentences.set(counter, ((String)sentences.get(counter)).substring(0, ((String)sentences.get(counter)).length() - 1));
                    pixelLength = (double)gTwo.getFontMetrics().stringWidth((String)sentences.get(counter) + "-");
                }

                sentences.add(newSentence);
                ++counter;
                pixelLength = (double)gTwo.getFontMetrics().stringWidth(newSentence);
                newSentence = "";
                if (pixelLength <= this.WIDTH * this.LEFT_PANEL_PERCENT - 20.0D - (double)this.TEXT_LEFT_SHIFT) {
                    cutUp = true;
                }
            }

            if (studentString.length() != 0) {
                for(j = 0; j < sentences.size(); ++j) {
                    if ((double)(this.tableScrollY - this.BORDER_WIDTH + (int)(this.textShift + this.HEIGHT * this.TOP_PANEL_PERCENT + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_TOP) + this.TEXT_HEIGHT * 2) >= (double)(-this.BORDER_WIDTH + (int)(this.HEIGHT * this.TOP_PANEL_PERCENT) + gTwo.getFontMetrics(this.generalFont).getHeight()) + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_TOP) {
                        sentences.set(j, ((String)sentences.get(j)).trim());
                        if (!((String)sentences.get(j)).substring(((String)sentences.get(j)).length() - 1).equals(",") && j != sentences.size() - 1) {
                            sentences.set(j, (String)sentences.get(j) + "-");
                        }

                        gTwo.drawString((String)sentences.get(j), this.TEXT_LEFT_SHIFT, this.tableScrollY - this.BORDER_WIDTH + (int)(this.textShift + this.HEIGHT * this.TOP_PANEL_PERCENT + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_TOP) + this.TEXT_HEIGHT * 2);
                    }

                    if (i == this.displayTableOrder.size() - 1 && j == sentences.size() - 1) {
                        this.tableBotY = this.tableScrollY - this.BORDER_WIDTH + (int)(this.textShift + this.HEIGHT * this.TOP_PANEL_PERCENT + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_TOP) + this.TEXT_HEIGHT * 2;
                    }

                    this.textShift += (double)this.TEXT_HEIGHT;
                }
            }

            this.textShift += (double)(this.TEXT_HEIGHT * 2);
        }

        gTwo.setFont(this.labelFont);
        gTwo.setColor(new Color(59, 59, 59));
        gTwo.fillRect(0, (int)(this.HEIGHT * this.TOP_PANEL_PERCENT + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_TOP), (int)(this.WIDTH * this.LEFT_PANEL_PERCENT), -this.BORDER_WIDTH + (int)(this.HEIGHT * this.TOP_PANEL_PERCENT + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_TOP) + this.TEXT_HEIGHT - (int)(this.HEIGHT * this.TOP_PANEL_PERCENT + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_TOP) + 3);
        gTwo.setColor(Color.WHITE);
        gTwo.drawString("TABLES", this.TEXT_LEFT_SHIFT, -this.BORDER_WIDTH + (int)(this.HEIGHT * this.TOP_PANEL_PERCENT + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_TOP) + this.TEXT_HEIGHT);
        gTwo.setColor(new Color(41, 41, 41));
        gTwo.fillRect(0, (int)(this.HEIGHT * this.TOP_PANEL_PERCENT + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_TOP + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_MID), (int)(this.WIDTH * this.LEFT_PANEL_PERCENT), (int)(this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_BOT));
        gTwo.setColor(new Color(59, 59, 59));
        gTwo.fillRect(this.BORDER_WIDTH, (int)(this.HEIGHT * this.TOP_PANEL_PERCENT + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_TOP + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_MID) + this.BORDER_WIDTH, (int)(this.WIDTH * this.LEFT_PANEL_PERCENT) - 2 * this.BORDER_WIDTH, (int)(this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_BOT) - 2 * this.BORDER_WIDTH);
        int[] triangleRightX = new int[]{(int)(this.WIDTH * this.LEFT_PANEL_PERCENT - (double)(this.TEXT_LEFT_SHIFT * 3 / 2) - (double)(this.TEXT_LEFT_SHIFT / 2)), (int)(this.WIDTH * this.LEFT_PANEL_PERCENT - (double)(this.TEXT_LEFT_SHIFT * 5 / 4) - (double)(this.TEXT_LEFT_SHIFT / 2)), (int)(this.WIDTH * this.LEFT_PANEL_PERCENT - (double)this.TEXT_LEFT_SHIFT - (double)(this.TEXT_LEFT_SHIFT / 2))};
        int[] triangleY = new int[]{(int)(this.HEIGHT * this.TOP_PANEL_PERCENT + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_TOP + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_MID) + this.TEXT_HEIGHT * 2 - 9, (int)(this.HEIGHT * this.TOP_PANEL_PERCENT + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_TOP + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_MID) + this.TEXT_HEIGHT * 2 - 16, (int)(this.HEIGHT * this.TOP_PANEL_PERCENT + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_TOP + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_MID) + this.TEXT_HEIGHT * 2 - 9};
        int[] triangleTwoY = new int[]{(int)(this.HEIGHT * this.TOP_PANEL_PERCENT + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_TOP + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_MID) + this.TEXT_HEIGHT * 2 - 6, (int)(this.HEIGHT * this.TOP_PANEL_PERCENT + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_TOP + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_MID) + this.TEXT_HEIGHT * 2 + 1, (int)(this.HEIGHT * this.TOP_PANEL_PERCENT + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_TOP + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_MID) + this.TEXT_HEIGHT * 2 - 6};
        gTwo.setColor(Color.WHITE);
        gTwo.drawString("PARAMETERS", this.TEXT_LEFT_SHIFT, (int)(this.HEIGHT * this.TOP_PANEL_PERCENT + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_TOP + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_MID) + this.TEXT_HEIGHT);
        gTwo.setFont(this.generalFont);
        gTwo.drawString("Room height: " + this.roomHeight + " cm", this.TEXT_LEFT_SHIFT, (int)(this.HEIGHT * this.TOP_PANEL_PERCENT + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_TOP + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_MID) + this.TEXT_HEIGHT * 2);
        gTwo.setColor(new Color(48, 48, 48));
        Rectangle tempRect = new Rectangle(triangleRightX[0] - this.TEXT_LEFT_SHIFT / 2, triangleY[1] - 5, triangleRightX[2] - triangleRightX[0] + this.TEXT_LEFT_SHIFT, triangleTwoY[1] - triangleY[1] + 10);
        gTwo.fill(tempRect);
        if (tempRect.contains(mouseClickXy[0], mouseClickXy[1]) && mousePressed) {
            if ((double)mouseClickXy[1] <= tempRect.getY() + tempRect.getHeight() / 2.0D) {
                gTwo.setColor(new Color(70, 70, 70));
                gTwo.fillRect((int)tempRect.getX(), (int)tempRect.getY(), (int)tempRect.getWidth(), (int)(tempRect.getHeight() / 2.0D));
                this.adjust(1, 0, 0, 0);
            } else {
                gTwo.setColor(new Color(70, 70, 70));
                gTwo.fillRect((int)tempRect.getX(), (int)tempRect.getY() + (int)tempRect.getHeight() / 2, (int)tempRect.getWidth(), (int)(tempRect.getHeight() / 2.0D));
                if (this.roomHeight > 1.0D) {
                    this.adjust(-1, 0, 0, 0);
                }
            }
        }

        gTwo.setColor(Color.WHITE);
        gTwo.fillPolygon(triangleRightX, triangleY, 3);
        gTwo.fillPolygon(triangleRightX, triangleTwoY, 3);
        gTwo.drawString("Room width: " + this.roomWidth + " cm", this.TEXT_LEFT_SHIFT, (int)(this.HEIGHT * this.TOP_PANEL_PERCENT + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_TOP + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_MID) + this.TEXT_HEIGHT * 3);

        for(t = 0; t < 3; ++t) {
            triangleY[t] += this.TEXT_HEIGHT;
            triangleTwoY[t] += this.TEXT_HEIGHT;
        }

        gTwo.setColor(new Color(48, 48, 48));
        tempRect = new Rectangle(triangleRightX[0] - this.TEXT_LEFT_SHIFT / 2, triangleY[1] - 5, triangleRightX[2] - triangleRightX[0] + this.TEXT_LEFT_SHIFT, triangleTwoY[1] - triangleY[1] + 10);
        gTwo.fill(tempRect);
        if (tempRect.contains(mouseClickXy[0], mouseClickXy[1]) && mousePressed) {
            if ((double)mouseClickXy[1] <= tempRect.getY() + tempRect.getHeight() / 2.0D) {
                gTwo.setColor(new Color(70, 70, 70));
                gTwo.fillRect((int)tempRect.getX(), (int)tempRect.getY(), (int)tempRect.getWidth(), (int)(tempRect.getHeight() / 2.0D));
                this.adjust(0, 1, 0, 0);
            } else {
                gTwo.setColor(new Color(70, 70, 70));
                gTwo.fillRect((int)tempRect.getX(), (int)tempRect.getY() + (int)tempRect.getHeight() / 2, (int)tempRect.getWidth(), (int)(tempRect.getHeight() / 2.0D));
                if (this.roomWidth > 1.0D) {
                    this.adjust(0, -1, 0, 0);
                }
            }
        }

        gTwo.setColor(Color.WHITE);
        gTwo.fillPolygon(triangleRightX, triangleY, 3);
        gTwo.fillPolygon(triangleRightX, triangleTwoY, 3);
        gTwo.drawString("Table radius: " + this.tableRadius + " cm", this.TEXT_LEFT_SHIFT, (int)(this.HEIGHT * this.TOP_PANEL_PERCENT + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_TOP + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_MID) + this.TEXT_HEIGHT * 4);

        for(t = 0; t < 3; ++t) {
            triangleY[t] += this.TEXT_HEIGHT;
            triangleTwoY[t] += this.TEXT_HEIGHT;
        }

        gTwo.setColor(new Color(48, 48, 48));
        tempRect = new Rectangle(triangleRightX[0] - this.TEXT_LEFT_SHIFT / 2, triangleY[1] - 5, triangleRightX[2] - triangleRightX[0] + this.TEXT_LEFT_SHIFT, triangleTwoY[1] - triangleY[1] + 10);
        gTwo.fill(tempRect);
        if (tempRect.contains(mouseClickXy[0], mouseClickXy[1]) && mousePressed) {
            if ((double)mouseClickXy[1] <= tempRect.getY() + tempRect.getHeight() / 2.0D) {
                gTwo.setColor(new Color(70, 70, 70));
                gTwo.fillRect((int)tempRect.getX(), (int)tempRect.getY(), (int)tempRect.getWidth(), (int)(tempRect.getHeight() / 2.0D));
                this.adjust(0, 0, 1, 0);
            } else {
                gTwo.setColor(new Color(70, 70, 70));
                gTwo.fillRect((int)tempRect.getX(), (int)tempRect.getY() + (int)tempRect.getHeight() / 2, (int)tempRect.getWidth(), (int)(tempRect.getHeight() / 2.0D));
                if (this.tableRadius > 20.0D) {
                    this.adjust(0, 0, -1, 0);
                }
            }
        }

        gTwo.setColor(Color.WHITE);
        gTwo.fillPolygon(triangleRightX, triangleY, 3);
        gTwo.fillPolygon(triangleRightX, triangleTwoY, 3);
        gTwo.drawString("Table walking radius: " + this.walkingRadius + " cm", this.TEXT_LEFT_SHIFT, (int)(this.HEIGHT * this.TOP_PANEL_PERCENT + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_TOP + this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) * this.DIVISION_PANEL_PERCENT_MID) + this.TEXT_HEIGHT * 5);

        for(t = 0; t < 3; ++t) {
            triangleY[t] += this.TEXT_HEIGHT;
            triangleTwoY[t] += this.TEXT_HEIGHT;
        }

        gTwo.setColor(new Color(48, 48, 48));
        tempRect = new Rectangle(triangleRightX[0] - this.TEXT_LEFT_SHIFT / 2, triangleY[1] - 5, triangleRightX[2] - triangleRightX[0] + this.TEXT_LEFT_SHIFT, triangleTwoY[1] - triangleY[1] + 10);
        gTwo.fill(tempRect);
        if (tempRect.contains(mouseClickXy[0], mouseClickXy[1]) && mousePressed) {
            if ((double)mouseClickXy[1] <= tempRect.getY() + tempRect.getHeight() / 2.0D) {
                gTwo.setColor(new Color(70, 70, 70));
                gTwo.fillRect((int)tempRect.getX(), (int)tempRect.getY(), (int)tempRect.getWidth(), (int)(tempRect.getHeight() / 2.0D));
                this.adjust(0, 0, 0, 1);
            } else {
                gTwo.setColor(new Color(70, 70, 70));
                gTwo.fillRect((int)tempRect.getX(), (int)tempRect.getY() + (int)tempRect.getHeight() / 2, (int)tempRect.getWidth(), (int)(tempRect.getHeight() / 2.0D));
                if (this.walkingRadius > 20.0D) {
                    this.adjust(0, 0, 0, -1);
                }
            }
        }

        gTwo.setColor(Color.WHITE);
        gTwo.fillPolygon(triangleRightX, triangleY, 3);
        gTwo.fillPolygon(triangleRightX, triangleTwoY, 3);
        gTwo.setColor(new Color(41, 41, 41));
        gTwo.fillRect(0, 0, (int)this.WIDTH, (int)(this.HEIGHT * this.TOP_PANEL_PERCENT));
        gTwo.setColor(new Color(59, 59, 59));
        gTwo.fillRect(this.BORDER_WIDTH, this.BORDER_WIDTH, (int)this.WIDTH - 2 * this.BORDER_WIDTH, (int)(this.HEIGHT * this.TOP_PANEL_PERCENT) - 2 * this.BORDER_WIDTH);
        Rectangle shiftButton = new Rectangle(20, 5, 150, (int)(this.HEIGHT * this.TOP_PANEL_PERCENT) - 10);
        gTwo.setFont(this.labelFont);
        gTwo.setColor(Color.WHITE);
        if (mousePressed && shiftButton.contains(mouseXy[0], mouseXy[1])) {
            if (this.buttonRefresh) {
                if (this.tableRearrangeMode) {
                    this.tableRearrangeMode = false;
                } else {
                    this.tableRearrangeMode = true;

                    for(int p = 0; p < this.drawTables.size(); ++p) {
                        if (((DrawnTable)this.drawTables.get(p)).getSelected()) {
                            ((DrawnTable)this.drawTables.get(p)).setSelected();
                        }
                    }

                    this.xAdjust = 0;
                    this.yAdjust = 0;
                }

                this.buttonRefresh = false;
            }

            gTwo.setColor(new Color(70, 70, 70));
            gTwo.fill(shiftButton);
            gTwo.setColor(new Color(41, 41, 41));
            gTwo.draw(shiftButton);
        } else {
            gTwo.setColor(new Color(45, 45, 45));
            gTwo.fill(shiftButton);
            gTwo.setColor(new Color(41, 41, 41));
            gTwo.draw(shiftButton);
        }

        gTwo.setColor(Color.WHITE);
        if (this.tableRearrangeMode) {
            gTwo.drawString("REARRANGE MODE", 20 + (150 - gTwo.getFontMetrics().stringWidth("REARRANGE MODE")) / 2, (int)(this.HEIGHT * this.TOP_PANEL_PERCENT) - 5 - this.TEXT_HEIGHT / 4);
        } else {
            gTwo.drawString("SHIFT MODE", 20 + (150 - gTwo.getFontMetrics().stringWidth("SHIFT MODE")) / 2, (int)(this.HEIGHT * this.TOP_PANEL_PERCENT) - 5 - this.TEXT_HEIGHT / 4);
        }

        Rectangle saveButton = new Rectangle(190, 5, 150, (int)(this.HEIGHT * this.TOP_PANEL_PERCENT) - 10);
        if (this.myKeyListener.getSaved()) {
            this.saveImage(-1);
        }

        if (mousePressed && saveButton.contains(mouseXy[0], mouseXy[1])) {
            if (this.buttonRefresh) {
                this.saveImage(-1);
                this.buttonRefresh = false;
            }

            gTwo.setColor(new Color(70, 70, 70));
            gTwo.fill(saveButton);
            gTwo.setColor(new Color(41, 41, 41));
            gTwo.draw(saveButton);
        } else {
            gTwo.setColor(new Color(45, 45, 45));
            gTwo.fill(saveButton);
            gTwo.setColor(new Color(41, 41, 41));
            gTwo.draw(saveButton);
        }

        gTwo.setColor(Color.WHITE);
        gTwo.drawString("SAVE", 190 + (150 - gTwo.getFontMetrics().stringWidth("SAVE")) / 2, (int)(this.HEIGHT * this.TOP_PANEL_PERCENT) - 5 - this.TEXT_HEIGHT / 4);
        Rectangle loadButton = new Rectangle(360, 5, 150, (int)(this.HEIGHT * this.TOP_PANEL_PERCENT) - 10);
        if (mousePressed && loadButton.contains(mouseXy[0], mouseXy[1])) {
            if (this.buttonRefresh) {
                this.loadImage(-1);
                this.buttonRefresh = false;
            }

            gTwo.setColor(new Color(70, 70, 70));
            gTwo.fill(loadButton);
            gTwo.setColor(new Color(41, 41, 41));
            gTwo.draw(loadButton);
        } else {
            gTwo.setColor(new Color(45, 45, 45));
            gTwo.fill(loadButton);
            gTwo.setColor(new Color(41, 41, 41));
            gTwo.draw(loadButton);
        }

        gTwo.setColor(Color.WHITE);
        gTwo.drawString("LOAD", 360 + (150 - gTwo.getFontMetrics().stringWidth("LOAD")) / 2, (int)(this.HEIGHT * this.TOP_PANEL_PERCENT) - 5 - this.TEXT_HEIGHT / 4);
        gTwo.setColor(Color.WHITE);

        for(i = 0; i < this.drawTables.size(); ++i) {
            for(j = 0; j < ((DrawnTable)this.drawTables.get(i)).getStudents().size(); ++j) {
                if (((DrawnStudent)((DrawnTable)this.drawTables.get(i)).getStudents().get(j)).getSelected()) {
                    if (!this.displayStudentOrder.contains(((DrawnTable)this.drawTables.get(i)).getStudents().get(j))) {
                        this.displayStudentOrder.add((DrawnStudent)((DrawnTable)this.drawTables.get(i)).getStudents().get(j));
                    }
                } else if (this.displayStudentOrder.contains(((DrawnTable)this.drawTables.get(i)).getStudents().get(j))) {
                    this.displayStudentOrder.remove(((DrawnTable)this.drawTables.get(i)).getStudents().get(j));
                }
            }

            if (((DrawnTable)this.drawTables.get(i)).getSelected()) {
                if (!this.displayTableOrder.contains(this.drawTables.get(i))) {
                    this.displayTableOrder.add((DrawnTable)this.drawTables.get(i));
                }
            } else if (this.displayTableOrder.contains(this.drawTables.get(i))) {
                this.displayTableOrder.remove(this.drawTables.get(i));
            }
        }

    }

    public void addTables(ArrayList<Table> tables) {
        this.drawTables.clear();

        for(int i = 0; i < tables.size(); ++i) {
            this.drawTables.add(new DrawnTable(((Table)tables.get(i)).getSize(), ((Table)tables.get(i)).getStudents()));
        }

        ArrayList<DrawnStudent> drawStudents = new ArrayList();

        int i;
        for(i = 0; i < this.drawTables.size(); ++i) {
            for(int j = 0; j < ((DrawnTable)this.drawTables.get(i)).getStudents().size(); ++j) {
                drawStudents.add(new DrawnStudent(((DrawnStudent)((DrawnTable)this.drawTables.get(i)).getStudents().get(j)).getName(), ((DrawnStudent)((DrawnTable)this.drawTables.get(i)).getStudents().get(j)).getStudentNumber(), ((DrawnStudent)((DrawnTable)this.drawTables.get(i)).getStudents().get(j)).getDietaryRestrictions()));
            }
        }

        this.hCircle = (int)((this.roomHeight - 2.0D * (this.tableRadius + this.walkingRadius) + 2.0D * (this.tableRadius + this.walkingRadius) * Math.sin(1.0471975511965976D)) / (2.0D * (this.tableRadius + this.walkingRadius) * Math.sin(1.0471975511965976D)));
        this.wCircle = (int)(this.roomWidth / (2.0D * (this.tableRadius + this.walkingRadius)));
        if (2.0D * (this.tableRadius + this.walkingRadius) * (double)this.wCircle + this.tableRadius + this.walkingRadius <= this.roomWidth) {
            this.wTwoCircle = this.wCircle;
        } else {
            this.wTwoCircle = this.wCircle - 1;
        }

        this.maxTableCapacity = this.wCircle * (int)Math.ceil((double)this.hCircle / 2.0D) + this.wTwoCircle * (int)Math.floor((double)this.hCircle / 2.0D);
        if (this.drawTables.size() <= this.maxTableCapacity) {
            System.out.print("The tables have successfully fit");
        } else {
            System.out.print("The tables cannot fit given the parameters. Please readjust and try again");
        }

        this.scalingWidth = this.WIDTH * (1.0D - this.LEFT_PANEL_PERCENT) / this.roomWidth;
        this.scalingHeight = this.HEIGHT * (1.0D - this.TOP_PANEL_PERCENT) / this.roomHeight;
        this.xPerm = (int)(this.WIDTH * this.LEFT_PANEL_PERCENT);
        this.yPerm = (int)(this.HEIGHT * this.TOP_PANEL_PERCENT);
        if (this.scalingWidth < this.scalingHeight) {
            this.scaling = this.scalingWidth;
        } else {
            this.scaling = this.scalingHeight;
        }

        this.offset = false;
        this.xCircleCount = 0;

        for(i = 0; i < this.drawTables.size(); ++i) {
            ((DrawnTable)this.drawTables.get(i)).setXPercent(1.0D * (double)this.x / this.roomWidth);
            ((DrawnTable)this.drawTables.get(i)).setYPercent(1.0D * (double)this.y / this.roomHeight);
            ++this.xCircleCount;
            if (!this.offset && this.xCircleCount < this.wCircle) {
                this.x += (int)((this.tableRadius + this.walkingRadius) * 2.0D);
            } else if (this.offset && this.xCircleCount < this.wTwoCircle) {
                this.x += (int)((this.tableRadius + this.walkingRadius) * 2.0D);
            } else {
                this.xCircleCount = 0;
                if (!this.offset) {
                    this.x = (int)(this.tableRadius + this.walkingRadius);
                    this.offset = true;
                } else {
                    this.x = 0;
                    this.offset = false;
                }

                this.y += (int)Math.sqrt(3.0D * (this.tableRadius + this.walkingRadius) * (this.tableRadius + this.walkingRadius));
            }
        }

        this.x = 0;
        this.y = 0;
        this.setStudentPos();
    }

    public boolean recalculate() {
        this.hCircle = (int)((this.roomHeight - 2.0D * (this.tableRadius + this.walkingRadius) + 2.0D * (this.tableRadius + this.walkingRadius) * Math.sin(1.0471975511965976D)) / (2.0D * (this.tableRadius + this.walkingRadius) * Math.sin(1.0471975511965976D)));
        this.wCircle = (int)(this.roomWidth / (2.0D * (this.tableRadius + this.walkingRadius)));
        if (2.0D * (this.tableRadius + this.walkingRadius) * (double)this.wCircle + this.tableRadius + this.walkingRadius <= this.roomWidth) {
            this.wTwoCircle = this.wCircle;
        } else {
            this.wTwoCircle = this.wCircle - 1;
        }

        this.maxTableCapacity = this.wCircle * (int)Math.ceil((double)this.hCircle / 2.0D) + this.wTwoCircle * (int)Math.floor((double)this.hCircle / 2.0D);
        boolean fit;
        if (this.drawTables.size() <= this.maxTableCapacity) {
            fit = true;
        } else {
            fit = false;
        }

        this.offset = false;
        this.xCircleCount = 0;

        for(int i = 0; i < this.drawTables.size(); ++i) {
            ((DrawnTable)this.drawTables.get(i)).setXPercent(1.0D * (double)this.x / this.roomWidth);
            ((DrawnTable)this.drawTables.get(i)).setYPercent(1.0D * (double)this.y / this.roomHeight);
            ++this.xCircleCount;
            if (!this.offset && this.xCircleCount < this.wCircle) {
                this.x += (int)((this.tableRadius + this.walkingRadius) * 2.0D);
            } else if (this.offset && this.xCircleCount < this.wTwoCircle) {
                this.x += (int)((this.tableRadius + this.walkingRadius) * 2.0D);
            } else {
                this.xCircleCount = 0;
                if (!this.offset) {
                    this.x = (int)(this.tableRadius + this.walkingRadius);
                    this.offset = true;
                } else {
                    this.x = 0;
                    this.offset = false;
                }

                this.y += (int)Math.sqrt(3.0D * (this.tableRadius + this.walkingRadius) * (this.tableRadius + this.walkingRadius));
            }
        }

        this.x = 0;
        this.y = 0;
        return fit;
    }

    public void adjust(int h, int w, int tr, int wr) {
        this.roomHeight += (double)h;
        this.roomWidth += (double)w;
        this.tableRadius += (double)tr;
        this.walkingRadius += (double)wr;
        if (!this.recalculate()) {
            this.roomHeight -= (double)h;
            this.roomWidth -= (double)w;
            this.tableRadius -= (double)tr;
            this.walkingRadius -= (double)wr;
            this.recalculate();
        }

    }

    public void saveImage(int thisSaveCount) {
        int imageScaling = 1;
        BufferedImage image = new BufferedImage((int)this.roomWidth * imageScaling, (int)this.roomHeight * imageScaling, 1);
        Graphics2D gT = (Graphics2D)image.getGraphics();
        gT.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gT.setColor(new Color(37, 37, 37));
        gT.fillRect(0, 0, (int)this.roomWidth * imageScaling, (int)this.roomHeight * imageScaling);

        int k;
        for(int i = 0; i < this.drawTables.size(); ++i) {
            gT.setColor(Color.LIGHT_GRAY);
            gT.drawOval((int)(Math.round(((DrawnTable)this.drawTables.get(i)).getXPercent() * this.roomWidth) * (long)imageScaling), (int)(Math.round(((DrawnTable)this.drawTables.get(i)).getYPercent() * this.roomHeight) * (long)imageScaling), (int)Math.round((this.tableRadius + this.walkingRadius) * 2.0D * (double)imageScaling), (int)Math.round((this.tableRadius + this.walkingRadius) * 2.0D * (double)imageScaling));
            gT.setColor(new Color(143, 175, 227));
            gT.fillOval((int)(Math.round(this.walkingRadius + ((DrawnTable)this.drawTables.get(i)).getXPercent() * this.roomWidth) * (long)imageScaling), (int)(Math.round(this.walkingRadius + ((DrawnTable)this.drawTables.get(i)).getYPercent() * this.roomHeight) * (long)imageScaling), (int)Math.round(this.tableRadius * 2.0D * (double)imageScaling), (int)Math.round(this.tableRadius * 2.0D * (double)imageScaling));
            double tempAngle = 6.283185307179586D / (double)((DrawnTable)this.drawTables.get(i)).getStudents().size();
            double currentAngle = 0.0D;

            int tempCentX;
            for(tempCentX = 0; tempCentX < ((DrawnTable)this.drawTables.get(i)).getStudents().size(); ++tempCentX) {
                gT.setFont(this.smallLabelFont);
                gT.setColor(new Color(14, 84, 110));
                gT.fillOval((int)Math.round((this.walkingRadius + this.tableRadius - this.tableRadius / 6.0D + Math.sin(currentAngle) * this.tableRadius + ((DrawnTable)this.drawTables.get(i)).getXPercent() * this.roomWidth) * (double)imageScaling), (int)Math.round(((DrawnTable)this.drawTables.get(i)).getYPercent() * this.roomHeight * (double)imageScaling) + (int)Math.round((this.walkingRadius + this.tableRadius - this.tableRadius / 6.0D + Math.cos(currentAngle) * this.tableRadius) * (double)imageScaling), (int)Math.round(this.tableRadius / 3.0D * (double)imageScaling), (int)Math.round(this.tableRadius / 3.0D * (double)imageScaling));
                currentAngle += tempAngle;
                gT.setColor(Color.BLACK);
                gT.drawString(((DrawnStudent)((DrawnTable)this.drawTables.get(i)).getStudents().get(tempCentX)).getPos(), (int)(this.tableRadius / 6.0D * (double)imageScaling) - (int)(1.0D * (double)gT.getFontMetrics().stringWidth(((DrawnStudent)((DrawnTable)this.drawTables.get(i)).getStudents().get(tempCentX)).getPos()) / 2.0D) + (int)((this.walkingRadius + this.tableRadius - this.tableRadius / 6.0D + Math.sin(currentAngle) * this.tableRadius + ((DrawnTable)this.drawTables.get(i)).getXPercent() * this.roomWidth) * (double)imageScaling), (int)(((DrawnTable)this.drawTables.get(i)).getYPercent() * this.roomHeight * (double)imageScaling) + (int)((this.walkingRadius + this.tableRadius - this.tableRadius / 6.0D + Math.cos(currentAngle) * this.tableRadius) * (double)imageScaling));
            }

            gT.setColor(Color.BLACK);
            gT.setFont(this.tableFont);
            tempCentX = (int)(this.tableRadius * (double)imageScaling) + (int)((this.walkingRadius + ((DrawnTable)this.drawTables.get(i)).getXPercent() * this.roomWidth) * (double)imageScaling);
            k = (int)(this.tableRadius * (double)imageScaling) + (int)((this.walkingRadius + ((DrawnTable)this.drawTables.get(i)).getYPercent() * this.roomHeight) * (double)imageScaling);
            gT.drawString(i + 1 + "", tempCentX - (int)(1.0D * (double)gT.getFontMetrics().stringWidth(i + 1 + "") / 2.0D), k + 7);
        }

        gT.dispose();

        try {
            PrintWriter output;
            if (thisSaveCount >= 0) {
                output = new PrintWriter((File)this.saveState.get(thisSaveCount));
            } else {
                output = new PrintWriter(new File("FloorPlanInfo.txt"));
            }

            output.println(this.drawTables.size());
            output.println(this.tableRadius);
            output.println(this.walkingRadius);
            output.println(this.roomHeight);
            output.println(this.roomWidth);

            for(int i = 0; i < this.drawTables.size(); ++i) {
                String studentInfoString = "";

                for(int j = 0; j < ((DrawnTable)this.drawTables.get(i)).getStudents().size(); ++j) {
                    String dietRestrict = "";

                    for(k = 0; k < ((DrawnStudent)((DrawnTable)this.drawTables.get(i)).getStudents().get(j)).getDietaryRestrictions().size(); ++k) {
                        if (k == 0) {
                            dietRestrict = (String)((DrawnStudent)((DrawnTable)this.drawTables.get(i)).getStudents().get(j)).getDietaryRestrictions().get(k);
                        } else {
                            dietRestrict = dietRestrict + "," + (String)((DrawnStudent)((DrawnTable)this.drawTables.get(i)).getStudents().get(j)).getDietaryRestrictions().get(k);
                        }
                    }

                    studentInfoString = studentInfoString + ((DrawnStudent)((DrawnTable)this.drawTables.get(i)).getStudents().get(j)).getStudentNumber() + ":" + ((DrawnStudent)((DrawnTable)this.drawTables.get(i)).getStudents().get(j)).getName() + ":" + dietRestrict + "|";
                }

                output.println(((DrawnTable)this.drawTables.get(i)).getXPercent());
                output.println(((DrawnTable)this.drawTables.get(i)).getYPercent());
                output.println(studentInfoString);
            }

            output.close();
        } catch (IOException var13) {
            System.out.println("Unable to generate file");
        }

        File floorPlan = new File("FloorPlan.png");

        try {
            ImageIO.write(image, "png", floorPlan);
        } catch (IOException var12) {
            System.out.print("Unable to find image");
        }

    }

    public void loadImage(int thisSaveCount) {
        for(int i = 0; i < this.drawTables.size(); ++i) {
            for(int j = 0; j < ((DrawnTable)this.drawTables.get(i)).getStudents().size(); ++j) {
                if (((DrawnStudent)((DrawnTable)this.drawTables.get(i)).getStudents().get(j)).getSelected()) {
                    ((DrawnStudent)((DrawnTable)this.drawTables.get(i)).getStudents().get(j)).setSelected();
                }
            }

            if (((DrawnTable)this.drawTables.get(i)).getSelected()) {
                ((DrawnTable)this.drawTables.get(i)).setSelected();
            }
        }

        try {
            this.displayStudentOrder.clear();
            this.displayTableOrder.clear();
            Scanner input;
            if (thisSaveCount >= 0) {
                input = new Scanner((File)this.saveState.get(thisSaveCount));
            } else {
                input = new Scanner(new File("FloorPlanInfo.txt"));
                this.clearAllStates();
            }

            this.drawTables.clear();
            int tableNum = Integer.parseInt(input.nextLine());
            this.tableRadius = Double.parseDouble(input.nextLine());
            this.walkingRadius = Double.parseDouble(input.nextLine());
            this.roomHeight = Double.parseDouble(input.nextLine());
            this.roomWidth = Double.parseDouble(input.nextLine());

            for(int i = 0; i < tableNum; ++i) {
                ArrayList<Student> newStudents = new ArrayList();
                String newNumber = "";
                String newName = "";
                ArrayList<String> arrayPref = new ArrayList();
                double newXPercent = Double.parseDouble(input.nextLine());
                double newYPercent = Double.parseDouble(input.nextLine());
                String mString = input.nextLine();

                while(mString.contains("|")) {
                    arrayPref.clear();
                    newNumber = mString.substring(0, mString.indexOf(":"));
                    mString = mString.substring(mString.indexOf(":") + 1);
                    newName = mString.substring(0, mString.indexOf(":"));
                    mString = mString.substring(mString.indexOf(":") + 1);

                    String stringPref;
                    for(stringPref = mString.substring(0, mString.indexOf("|")); stringPref.contains(","); stringPref = stringPref.substring(stringPref.indexOf(",") + 1)) {
                        arrayPref.add(stringPref.substring(0, stringPref.indexOf(",")));
                    }

                    arrayPref.add(stringPref);
                    mString = mString.substring(mString.indexOf("|") + 1);
                    ArrayList<String> friends = new ArrayList();
                    newStudents.add(new Student(newName, newNumber, arrayPref, friends));
                }

                this.drawTables.add(new DrawnTable(newStudents.size(), newStudents));
                ((DrawnTable)this.drawTables.get(i)).setXPercent(newXPercent);
                ((DrawnTable)this.drawTables.get(i)).setYPercent(newYPercent);
            }

            input.close();
        } catch (IOException var16) {
            System.out.println("File not found");
        }

        this.setStudentPos();
        if (thisSaveCount < 0) {
            this.saveCount = 0;
            this.saveImage(0);
        }

    }

    public void setStudentPos() {
        for(int i = 0; i < this.drawTables.size(); ++i) {
            for(int j = 0; j < ((DrawnTable)this.drawTables.get(i)).getStudents().size(); ++j) {
                ((DrawnStudent)((DrawnTable)this.drawTables.get(i)).getStudents().get(j)).setPos(i + 1 + "." + (j + 1));
            }
        }

    }

    public void save() {
        if (this.saveCount == 9) {
            for(int i = 0; i < this.saveState.size() - 1; ++i) {
                try {
                    Scanner input = new Scanner((File)this.saveState.get(i + 1));
                    PrintWriter output = new PrintWriter((File)this.saveState.get(i));

                    while(input.hasNext()) {
                        output.println(input.nextLine());
                    }

                    input.close();
                    output.close();
                } catch (IOException var4) {
                    System.out.println("The save texts do not exist");
                }
            }
        }

        if (this.saveCount < 9) {
            ++this.saveCount;
        }

        this.saveImage(this.saveCount);
    }

    public void controlZ() {
        if (this.saveCount > 0) {
            --this.saveCount;
            this.loadImage(this.saveCount);
            ++this.ctrlZNum;
        }

    }

    public void controlY() {
        try {
            Scanner input = new Scanner((File)this.saveState.get(this.saveCount + 1));
            if (this.saveCount < 9 && this.ctrlZNum > 0) {
                if (input.hasNext()) {
                    ++this.saveCount;
                    this.loadImage(this.saveCount);
                }

                --this.ctrlZNum;
            }
        } catch (IOException var2) {
            System.out.println("Unable to find file");
        }

    }

    public void clearAllStates() {
        for(int i = 0; i < this.saveState.size(); ++i) {
            try {
                PrintWriter output = new PrintWriter((File)this.saveState.get(i));
                output.close();
            } catch (IOException var4) {
                System.out.println("Unable to generate file");
            }
        }

    }

    public boolean checkConstant() {
        boolean match = true;

        try {
            PrintWriter output = new PrintWriter(new File("test.txt"));
            output.println(this.drawTables.size());
            output.println(this.tableRadius);
            output.println(this.walkingRadius);
            output.println(this.roomHeight);
            output.println(this.roomWidth);

            String oldString;
            for(int i = 0; i < this.drawTables.size(); ++i) {
                oldString = "";

                for(int j = 0; j < ((DrawnTable)this.drawTables.get(i)).getStudents().size(); ++j) {
                    String dietRestrict = "";

                    for(int k = 0; k < ((DrawnStudent)((DrawnTable)this.drawTables.get(i)).getStudents().get(j)).getDietaryRestrictions().size(); ++k) {
                        if (k == 0) {
                            dietRestrict = (String)((DrawnStudent)((DrawnTable)this.drawTables.get(i)).getStudents().get(j)).getDietaryRestrictions().get(k);
                        } else {
                            dietRestrict = dietRestrict + "," + (String)((DrawnStudent)((DrawnTable)this.drawTables.get(i)).getStudents().get(j)).getDietaryRestrictions().get(k);
                        }
                    }

                    oldString = oldString + ((DrawnStudent)((DrawnTable)this.drawTables.get(i)).getStudents().get(j)).getStudentNumber() + ":" + ((DrawnStudent)((DrawnTable)this.drawTables.get(i)).getStudents().get(j)).getName() + ":" + dietRestrict + "|";
                }

                output.println(((DrawnTable)this.drawTables.get(i)).getXPercent());
                output.println(((DrawnTable)this.drawTables.get(i)).getYPercent());
                output.println(oldString);
            }

            output.close();
            Scanner inputCurrent = new Scanner(new File("test.txt"));
            Scanner inputOld = new Scanner((File)this.saveState.get(this.saveCount));
            String newString = "";
            oldString = "";

            while(inputCurrent.hasNext() || inputOld.hasNext()) {
                if (inputCurrent.hasNext()) {
                    newString = inputCurrent.nextLine();
                }

                if (inputOld.hasNext()) {
                    oldString = inputOld.nextLine();
                }

                if (!newString.equals(oldString)) {
                    match = false;
                }
            }
        } catch (IOException var10) {
            System.out.println("Unable to generate file");
        }

        return match;
    }
}
