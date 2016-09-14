/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bioguide.base;

import java.awt.event.MouseEvent;
import y.view.EditMode;

import y.view.HitInfo;




/**
 *
 * @author Akif
 */
public class MyEditMode extends EditMode {
    @Override
  public void mousePressed(MouseEvent ev) {

      if (this.getHitInfo(ev).getHitEdge()!=null) {


    } else {
      // let EditMode handle the click event
      super.mousePressed(ev);
    }

  }

        @Override
  public void mouseMoved(MouseEvent ev) {

      if (this.getHitInfo(ev).getHitEdge()!=null) {


    } else {
      // let EditMode handle the click event
      super.mouseMoved(ev);
    }

  }

                @Override

  public void mouseReleasedLeft(double x, double y) {

      if (this.getHitInfo(x,y).getHitEdge()!=null) {


    } else {
      // let EditMode handle the click event
      super.mouseReleasedLeft(x,y);
    }

  }

}
