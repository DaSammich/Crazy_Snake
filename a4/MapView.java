package a4;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

// FWI this width and height of this panel are 792 and 1017 respectively
public class MapView extends JPanel implements IObserver, MouseListener, MouseMotionListener, MouseWheelListener {

      private IGameWorld gw;
      private AffineTransform worldToND, ndToScreen, theVTM, zoomXform, panXform, inverseVTM;
      private double zoom = .75, zoomInc = .035;
      private double currX = 0.15, currY = 0.15;
      private double panX = 0.15, panY = 0.15;
      private boolean ctrlFlag = false;

      // make some cursors
      private Image grabImg;
      private Image handImg;
      private Image pointImg;
      private Cursor grabCursor;
      private Cursor handCursor;
      private Cursor pointCursor;

      // the starting location is for the panning
      private Point startLoc;
      private Point mouseWorldLoc, mouseScreenLoc;

      public MapView(IGameWorld gw) {
            this.setBorder(new LineBorder(Color.gray, 2));
            this.gw = gw;

            // make the transformations
            worldToND = new AffineTransform();
            ndToScreen = new AffineTransform();
            theVTM = new AffineTransform();
            zoomXform = new AffineTransform();
            panXform = new AffineTransform();

            // add the Mouse wheel listener
            this.addMouseWheelListener(this);
            this.addMouseMotionListener(this);

            // build those cursor images
            try {
                  grabImg = ImageIO.read(new File("cursors" + File.separator + "cursor_drag_hand.png"));
                  handImg = ImageIO.read(new File("cursors" + File.separator + "cursor_hand.png"));
                  pointImg = ImageIO.read(new File("cursors" + File.separator + "hand_2_icon.png"));
            } catch (Exception e) {
                  e.printStackTrace();
            }
            // create the cursors
            grabCursor = Toolkit.getDefaultToolkit().createCustomCursor(grabImg, new Point(0, 0), "grab");
            handCursor = Toolkit.getDefaultToolkit().createCustomCursor(handImg, new Point(0, 0), "grab");
            pointCursor = Toolkit.getDefaultToolkit().createCustomCursor(pointImg, new Point(15, 0), "grab");
      }

      public AffineTransform buildWorldToNDXform(int w, int h, int l, int b) {
            AffineTransform xForm = new AffineTransform();
            xForm.translate(0, 0);
            xForm.scale(1.0 / this.getWidth(), 1.0 / this.getHeight());
            return xForm;
      }

      public AffineTransform buildNDtoScreenXform(int w, int h) {
            AffineTransform xForm = new AffineTransform();

            // the "flipping" of the screen, note the order even
            // in this submodule, we scale first then translate
            xForm.translate(0, this.getHeight());
            xForm.scale(this.getWidth(), -this.getHeight());

            return xForm;
      }

      public AffineTransform buildZoomXform(double z) {
            AffineTransform xForm = new AffineTransform();
            xForm.scale(z, z);

            return xForm;
      }

      public AffineTransform buildPanXform(double dx, double dy) {
            AffineTransform xForm = new AffineTransform();
            xForm.translate(dx, dy);
            return xForm;
      }

      @Override
      public void update() {
            repaint();
      }

      @Override
      public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            AffineTransform saveAt = g2d.getTransform();

            // use the factory method to make these x forms
            // take care of the window transforms
            // make a new transform for panning and zooming
            worldToND = buildWorldToNDXform(this.getWidth(), this.getHeight(), 0, this.getHeight());
            ndToScreen = buildNDtoScreenXform(this.getWidth(), this.getHeight());
            zoomXform = buildZoomXform(zoom);
            panXform = buildPanXform(panX, panY);

            theVTM = (AffineTransform) ndToScreen.clone();
            theVTM.concatenate(panXform);
            theVTM.concatenate(zoomXform);
            theVTM.concatenate(worldToND);
            // apply the x forms
            g2d.transform(theVTM);

            // for every game object, if it's selectable and 
            // and its selected, then draw the highlighted version
            // otherwise draw its normal version
            // and update it to allow it to work while the timer
            // has stopped (pause mode)
            for (GameObject o : gw.getWorld()) {
                  if (o instanceof IDrawable) {
                        if (o instanceof ISelectable
                                && ((ISelectable) o).isSelected()
                                && gw.isPaused()) {
                              ((ISelectable) o).selectedDraw(g2d);
                              update();
                        } else {
                              ((IDrawable) o).draw(g2d);
                              // after drawing the objects, change their coordinates and 
                              // sizes to world coordinates and sizes 

                              update();
                        }
                  }
            }

            g2d.setTransform(saveAt);
      }

      private void deselectAll() {
            for (GameObject o2 : gw.getWorld()) {
                  if (o2 instanceof ISelectable) {
                        ((ISelectable) o2).turnOff();
                  }
            }
      }

      @Override
      public void mouseClicked(MouseEvent e) {

            // get the mouse screen's coordinates
            mouseScreenLoc = e.getPoint();

            // if shift is down, we make a new sweeper there
            // note that due to transform, we have to compensate that for the y value
            if (e.isShiftDown() && gw.isPaused()) {
                  gw.makeSweeper(e.getPoint().x, -e.getPoint().y + this.getHeight());
            }

            // try to create the inverse of our vtm, since 
            // all of our transformations is done through the vtm
            try {
                  inverseVTM = theVTM.createInverse();
            } catch (NoninvertibleTransformException error) {
                  error.printStackTrace();
            }
            // get the mouse coordinates as world coordinates
            Point2D mouseWorldLoc = inverseVTM.transform(mouseScreenLoc, null);

//            System.out.println(mouseWorldLoc);
//            System.out.println(gw.getSweeper().contains(mouseWorldLoc));
            if (gw.isPaused()) {
                  // check to see if the ctrl key is being pressed
                  // this is what we use to find the modifiers for clicking

                  if ((e.isControlDown())) {
                        ctrlFlag = true;
                  } else {
                        ctrlFlag = false;
                  }
                  // this will show whether the flag was set
//                  System.out.println("flag " + ctrlFlag);

                  // check if anything is selected
                  boolean isSomethingSelected = false;

                  for (GameObject o : gw.getWorld()) {

                        if (o instanceof ISelectable) {
                              // do this if the point is contained in the shape
                              if (((ISelectable) o).contains(mouseWorldLoc)) {

                                    isSomethingSelected = true;

                                    // turn all highlighting off everything time this runs
                                    // so that only 1 thing will be highlighted at a single time,
                                    // unless ctrl is pressed at the same time
                                    // if it's already selected, then turn it off
                                    // this way you can click on something twice and it 
                                    // if it is already highlighted it will turn off
                                    if (((ISelectable) o).isSelected()) {
                                          ISelectable turnedOn = (ISelectable) o;
                                          turnedOn.setSelected(false);
                                    } else if (!ctrlFlag) {
                                          // otherwise, turn everything else off and 
                                          // turn that new thing on
                                          deselectAll();
                                          // if it's not selected, select it
                                          if (!((ISelectable) o).isSelected()) {
                                                ISelectable turnedOn = (ISelectable) o;
                                                turnedOn.setSelected(true);

                                                // if you select something, make everything else not
                                                // selected, or turn off every other object
                                          } // otherwise, deselect it
                                          else {
                                                ((ISelectable) o).setSelected(false);
                                          }

                                    } else {

                                          // if it's not selected, select it
                                          if (!((ISelectable) o).isSelected()) {
                                                ISelectable turnedOn = (ISelectable) o;
                                                turnedOn.setSelected(true);

                                                // if you select something, make everything else not
                                                // selected, or turn off every other object
                                          } // otherwise, deselect it
                                          else {
                                                ((ISelectable) o).setSelected(false);
                                          }

                                    }

                              }
                        }
                  }
                  // you go here if you didn't click an object
                  if (!isSomethingSelected) {
                        deselectAll();
                  }

            }

      }

      @Override
      public void mousePressed(MouseEvent e) {
            // get the starting location of the drag,
            // we will us ethis later for panning
            startLoc = e.getPoint();
//            System.out.println(startLoc);
//            System.out.println(gw.getSweeper().contains(startLoc));

      }

      @Override
      public void mouseReleased(MouseEvent e) {
            // save the state of the panned translation
            // so it will not reset each time
            currX = panX;
            currY = panY;

            if (!gw.isPaused()) {
                  this.setCursor(handCursor);
            } else {
                  this.setCursor(pointCursor);
            }
      }

      // rest of mouse methods
      @Override
      public void mouseEntered(MouseEvent e) {
            if (!gw.isPaused()) {
                  this.setCursor(handCursor);
            } else {
                  this.setCursor(pointCursor);
            }

      }

      @Override
      public void mouseExited(MouseEvent e) {

      }

      @Override
      public void mouseMoved(MouseEvent e) {

      }

      @Override
      public void mouseDragged(MouseEvent e) {
            // only allow panning in pause
            if (!gw.isPaused()) {
                  // make the pannings
                  panX = (e.getPoint().x - startLoc.x) / 1000.0 + currX;
                  // remember the y axis is inverted from our previous transform
                  // hence the inverse of the signs 
                  panY = (-e.getPoint().y + startLoc.y) / 1000.0 + currY;

                  this.setCursor(grabCursor);
            }

      }

      @Override
      public void mouseWheelMoved(MouseWheelEvent e) {
            // scrolling up is -1, scrolling down is +1
            // get scroll amount is 3 by default            
            if (!gw.isPaused()) {
                  if (e.getWheelRotation() < 0) {
                        zoom += zoomInc;
                  } else {
                        zoom -= zoomInc;
                  }
            }
      }
}
