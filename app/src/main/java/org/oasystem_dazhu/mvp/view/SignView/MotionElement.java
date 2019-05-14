package org.oasystem_dazhu.mvp.view.SignView;

/**
 */
public class MotionElement {

        public float x;
        public float y;
        //压力值  物理设备决定的，和设计的设备有关系，在此Demo中没有用到 ，但是这个坑  记录下
        public float pressure;
        //绘制的工具是否是手指或者是笔（触摸笔）
        public int toolType;

        public MotionElement(float mx, float my, float mp, int ttype) {
            x = mx;
            y = my;
            pressure = mp;
            toolType = ttype;
        }


}
