package lucas.com.footballdemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * 足球线路绘制视图
 */

public class FootballLineLayout extends RelativeLayout {


    private double proportionX;
    private double proportionY;
    private float xy;
    private int stateX;
    private int stateY;
    private ArrayList<ShootEntity> linePointList;

    public FootballLineLayout(Context context) {
        super(context);
    }

    public FootballLineLayout(Context contexts, AttributeSet attrs) {
        super(contexts, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setData(ArrayList<ShootEntity> linePointList, Activity activity) {
        removeAllViews();
        j = 0;
        this.linePointList = linePointList;
        ViewGroup.LayoutParams params = getLayoutParams();
        params.width = ScreenUtils.getScreenWidth(activity)-ScreenUtils.dip2px(activity, 10);
        //球场角球点起算
        params.height = params.width*594/983;
        setLayoutParams(params);
        proportionX = MyBigDecimal.div(867,983);
        proportionY = MyBigDecimal.div(561,594);
        double width = MyBigDecimal.mul(params.width, proportionX);
        double height = MyBigDecimal.mul(params.height, proportionY);
        xy = (float)MyBigDecimal.div(10500, width);
        stateX = (int)(params.width-width)/2;
        stateY = (int)(params.height-height)/2;

    }

    private int j = 0;
    private Paint paint = new Paint();//画线的画笔
    private float shootPointX;//球的线路点X坐标
    private float shootPointY;//球的线路点Y坐标
    private int nextShootPointX;//球的线路下一个点X坐标
    private int nextShootPointY;//球的线路下一个点Y坐标
    private int speed;///绘制速度
    private int length;//绘制长度
    private int time;//绘制时间
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setAntiAlias(true);
        if(linePointList!=null && linePointList.size()>0){
            for (int i = 0; i <= j&&i<linePointList.size()-1; i++) {
                shootPointX = (int)MyBigDecimal.div(linePointList.get(i).getLineXpos(),xy)+stateX;
                shootPointY = (int)MyBigDecimal.div(linePointList.get(i).getLineYpos(),xy)+stateY;
                nextShootPointX = (int)MyBigDecimal.div(linePointList.get(i+1).getLineXpos(),xy)+stateX;
                nextShootPointY = (int)MyBigDecimal.div(linePointList.get(i+1).getLineYpos(),xy)+stateY;
                paint.setColor(Color.BLACK);
                paint.setStrokeWidth(3);
                canvas.drawLine(shootPointX, shootPointY, nextShootPointX, nextShootPointY, paint);
            }

            length = (int) Math.sqrt(MyBigDecimal.mul(nextShootPointX-shootPointX, nextShootPointX-shootPointX)+MyBigDecimal.mul(nextShootPointY-shootPointY, nextShootPointY-shootPointY));
            speed = 2;
            time = (int)MyBigDecimal.div(length, speed);

            if(j < linePointList.size()-2){
                new Handler().postDelayed(new Runnable(){

                    public void run() {   
                        j++;
                        invalidate();
                    }   
                }, time);
            }else{
                j = 0;
            }
        }
    }
}
