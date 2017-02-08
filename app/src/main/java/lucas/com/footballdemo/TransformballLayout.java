package lucas.com.footballdemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

/**
 * 足球实践点任务绘制视图
 */

public class TransformballLayout extends RelativeLayout {


    private double proportionX;
    private double proportionY;
    private float xy;
    private int stateX;
    private int stateY;
    private Activity activitys;
    private ArrayList<ShootEntity> linePointList;
    private ArrayList<ShootEntity> eventList;
    private int nameLeft;
    private int nameTop;
    private int nameRight;
    private int nameBottom;
    private String HOMETEAMID = "1";

    public TransformballLayout(Context context) {
        super(context);
    }

    public TransformballLayout(Context contexts, AttributeSet attrs) {
        super(contexts, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setData(ArrayList<ShootEntity> linePointList, ArrayList<ShootEntity> eventList, Activity activity) {
        removeAllViews();
        removeAllViewsInLayout();
        this.activitys = activity;
        this.linePointList = linePointList;
        this.eventList = eventList;
        shoot_photo = (NetworkImageView)activitys.findViewById(R.id.shoot_photo);
        name = (TextView)activitys.findViewById(R.id.name);
        event_name = (TextView)activitys.findViewById(R.id.event_name);
        place = (TextView)activitys.findViewById(R.id.place);
        distance = (TextView)activitys.findViewById(R.id.distance);
        type = (TextView)activitys.findViewById(R.id.type);
        footgoal = (FootballGoalLayout)activitys.findViewById(R.id.footgoal);
        shoot_message = (LinearLayout)activitys.findViewById(R.id.shoot_message);
        x = 0;
        n = 0;
        j = 0;
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
        smallpicXY = 42;
        bigpicXY = 60;
        creatBitmap();
        if (linePointList == null || linePointList.isEmpty()) {
            return;
        }
        //这里做一个重用view的处理
        int l = 0;
        while (l < eventList.size()) {
            ImageView iv = new ImageView(activitys);
            addView(iv,generateDefaultLayoutParams());
            l++;
        }
        int k = 0;
        while (k < eventList.size()) {
            TextView tv = new TextView(activitys);
            addView(tv,generateDefaultLayoutParams());
            k++;
        }
        
        int m = 0;
        while (m < eventList.size()) {
            TextView iv = new TextView(activitys);
            addView(iv,generateDefaultLayoutParams());
            m++;
        }
        
        for (int i = 0; i < eventList.size(); i++) {
            ImageView iv = (ImageView)getChildAt(i);
            final ShootEntity entity = eventList.get(i);
            iv.setOnClickListener(new OnClickListener() {
                
                @Override
                public void onClick(View v) {
                    if(entity.getPlayID().equals("1")){
                        footgoal.setVisibility(View.VISIBLE);
                        footgoal.removeAllViews();
                        shoot_message.setVisibility(View.GONE);
                        footgoal.goal(entity, activitys);
                    }else{
                        shoot_photo.setDefaultImageResId(R.mipmap.shoot_player);
                        name.setText(entity.getPlayerName1());
                        event_name.setText(entity.getEventName());
                        place.setText(activitys.getResources().getString(R.string.part)+entity.getBodyPartyName());
                        distance.setText(activitys.getResources().getString(R.string.distance)+entity.getDistanceName());
                        type.setText(activitys.getResources().getString(R.string.type)+entity.getBallTypeName());
                        footgoal.setVisibility(View.GONE);
                        shoot_message.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
        
        
        
        
    }

    public void setPlayerPic(ShootEntity shootEntity) {

        if(shootEntity.getLineEventID()!=0){
            for (int i = 0; i < eventList.size(); i++) {
                int eventID = eventList.get(i).getEventID();
                if(shootEntity.getLineEventID() == eventID){
                    playerName = eventList.get(i).getPlayerName1();
                    eventName = eventList.get(i).getEventName();
                    textPlace = eventList.get(i).getBodyPartyName();
                    textDistance = eventList.get(i).getDistanceName();
                    textType = eventList.get(i).getBallTypeName();
                    number = eventList.get(i).getNumberBack1();
                    shoot_photo.setDefaultImageResId(R.mipmap.shoot_player);
                    name.setText(playerName);
                    event_name.setText(eventName);
                    place.setText(activitys.getResources().getString(R.string.part)+textPlace);
                    distance.setText(activitys.getResources().getString(R.string.distance)+textDistance);
                    type.setText(activitys.getResources().getString(R.string.type)+textType);
                    if(eventList.get(i).getPlayID().equals("2")){
                        footgoal.setVisibility(View.VISIBLE);
                        shoot_message.setVisibility(View.GONE);
                        footgoal.goal(eventList.get(i), activitys);
                    }else{
                        footgoal.setVisibility(View.GONE);
                        shoot_message.setVisibility(View.VISIBLE);
                    }
                }
            }

            ImageView iv = (ImageView)getChildAt(x);
            TextView tv = (TextView)getChildAt(x+eventList.size());
            TextView tvName = (TextView)getChildAt(x+eventList.size()+eventList.size());
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
            int shootPointX = (int)MyBigDecimal.div(shootEntity.getLineXpos(),xy)+stateX;
            int shootPointY = (int)MyBigDecimal.div(shootEntity.getLineYpos(),xy)+stateY;
            if(shootEntity.getTeamID().equals(HOMETEAMID)){
                iv.setBackgroundResource(R.mipmap.uniform02);
            }else{
                iv.setBackgroundResource(R.mipmap.keeper02);
            }
            iv.layout((int)shootPointX, (int)shootPointY, (int)shootPointX+90, (int)shootPointY+90);
            tv.setText(number);
            if(number.length()>1){
                tv.layout((int)shootPointX+21, (int)shootPointY+15, (int)shootPointX+90, (int)shootPointY+90);
            }else{
                tv.layout((int)shootPointX+33, (int)shootPointY+15, (int)shootPointX+90, (int)shootPointY+90);
            }
            tvName.setGravity(Gravity.CENTER);
            tvName.setTextColor(Color.WHITE);
            tvName.setBackgroundColor(Color.argb(180, 0, 0, 0));
            tvName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
            tvName.setText(eventName);
            
            if(eventName.length()==2){
                nameLeft = shootPointX+15;
                nameTop = shootPointY+93;
                nameRight = shootPointX+75;
            }else if(eventName.length()==3){
                nameLeft = shootPointX;
                nameTop = shootPointY+93;
                nameRight = shootPointX+90;
            }else if(eventName.length()==4){
                nameLeft = shootPointX-15;
                nameTop = shootPointY+93;
                nameRight = shootPointX+105;
            }else if(eventName.length()==5){
                nameLeft = shootPointX-30;
                nameTop = shootPointY+93;
                nameRight = shootPointX+120;
            }
            nameBottom = nameTop+36;
            tvName.layout(nameLeft, nameTop, nameRight, nameBottom);
            
            if(x<eventList.size()-1){
                x++;
            }else{
                x = eventList.size()-1;
            }
        }
    }

    private int j = 0;
    private Paint paint = new Paint();
    private int speed;
    private int smallpicXY;
    private int bigpicXY;
    private Bitmap smallballbitmap;
    private Bitmap bigballbitmap;
    private float shootPointX;
    private float shootPointY;
    private int nextShootPointX;
    private int nextShootPointY;
    private int length;
    private int time;
    private LinearLayout shoot_message;
    private NetworkImageView shoot_photo;
    private TextView name;
    private TextView event_name;
    private TextView place;
    private TextView distance;
    private TextView type;
    private String playerName;
    private String eventName = "";
    private String textPlace;
    private String textDistance;
    private String textType;
    private int x = 0;
    private String number = "";
    private FootballGoalLayout footgoal;
    private int n = 0;

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
                if(linePointList.get(i).getLineEventID()!=0){
                    canvas.drawBitmap(smallballbitmap, shootPointX-24, shootPointY-24, paint);
                }
            }
            canvas.drawBitmap(bigballbitmap, nextShootPointX-36, nextShootPointY-36, paint);

            length = (int) Math.sqrt(MyBigDecimal.mul(nextShootPointX-shootPointX, nextShootPointX-shootPointX)+MyBigDecimal.mul(nextShootPointY-shootPointY, nextShootPointY-shootPointY));
            speed = 3;
            time = (int)MyBigDecimal.div(length, speed);

            j++;
            if(j < linePointList.size()){
                new Handler().postDelayed(new Runnable(){

                    public void run() {   
                        setPlayerPic(linePointList.get(n));
                        if(n<linePointList.size()-1){
                            n++;
                        }else{
                            n = linePointList.size()-1;
                        }
                        invalidate();
                    }   
                }, time);
            }else if(j == linePointList.size()){
                setPlayerPic(linePointList.get(linePointList.size()-1));
            }else{
                j = 999;
            }
        }

    }


    private void creatBitmap() {
        Bitmap ballbitmap = BitmapFactory.decodeResource(activitys.getResources(), R.mipmap.soccer_b03_hdpi);
        int ballwidth = ballbitmap.getWidth();    
        int ballheight = ballbitmap.getHeight();    
        // 计算缩放比例    
        float scaleWidth1 = ((float) smallpicXY) / ballwidth;    
        float scaleHeight1 = ((float) smallpicXY) / ballheight;
        Matrix matrix1 = new Matrix();
        matrix1.postScale(scaleWidth1,scaleHeight1);    
        // 得到新的图片    
        smallballbitmap = Bitmap.createBitmap(ballbitmap, 0, 0, ballwidth, ballheight, matrix1, true);
        // 计算缩放比例    
        float scaleWidth2 = ((float) bigpicXY) / ballwidth;    
        float scaleHeight2 = ((float) bigpicXY) / ballheight;
        Matrix matrix2 = new Matrix();
        matrix2.postScale(scaleWidth2,scaleHeight2);    
        bigballbitmap = Bitmap.createBitmap(ballbitmap, 0, 0, ballwidth, ballheight, matrix2, true);


    }
}
