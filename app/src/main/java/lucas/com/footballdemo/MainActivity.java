package lucas.com.footballdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FootballLineLayout footline;
    private TransformballLayout transformfoot;
    private ImageView restart;
    private ImageView reback;
    ArrayList<ShootEntity> eventList;
    ArrayList<ShootEntity> pointEvent;
    private FootballGoalLayout footgoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);//需要添加的语句

        setView();
        setListener();

        getData();

    }

    private void setView() {
        restart = (ImageView)findViewById(R.id.restart);
        reback = (ImageView)findViewById(R.id.reback);
        footgoal =(FootballGoalLayout)findViewById(R.id.footgoal);
        footline = (FootballLineLayout)findViewById(R.id.footline);
        transformfoot = (TransformballLayout)findViewById(R.id.transformfoot);
        restart.setVisibility(View.VISIBLE);
    }

    private void setListener() {
        restart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                footgoal.removeAllViews();
                footgoal.setVisibility(View.INVISIBLE);
                footline.setData(pointEvent, MainActivity.this);
                transformfoot.setData(pointEvent,eventList, MainActivity.this);
            }
        });

        reback.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getData() {

        //测试数据

        eventList = new ArrayList<ShootEntity>();
        pointEvent = new ArrayList<ShootEntity>();
        ShootEntity entity = new ShootEntity();
        entity.setEventID(1);
        entity.setPlayID("0");
        entity.setEventName("传球");
        entity.setTeamID("1");
        entity.setPlayerName1("张三");
        entity.setNumberBack1("5");
        entity.setBodyPartyName("脚背");
        entity.setDistanceName("50米");
        entity.setBallTypeName("长传");
        entity.setIsOK("1");
        entity.setBallXPos(400);
        entity.setBallYPos(200);
        entity.setFrameXPos(400);
        entity.setFrameYPos(200);
        entity.setGkXPos(400);
        entity.setGkYPos(200);
        eventList.add(entity);

        ShootEntity entity1 = new ShootEntity();
        entity1.setEventID(2);
        entity1.setPlayID("0");
        entity1.setEventName("抢断");
        entity1.setTeamID("2");
        entity1.setPlayerName1("李四");
        entity1.setNumberBack1("8");
        entity1.setBodyPartyName("脚背");
        entity1.setDistanceName("10米");
        entity1.setBallTypeName("滑铲");
        entity1.setIsOK("0");
        entity1.setBallXPos(800);
        entity1.setBallYPos(300);
        entity1.setFrameXPos(800);
        entity1.setFrameYPos(300);
        entity1.setGkXPos(800);
        entity1.setGkYPos(300);
        eventList.add(entity1);

        ShootEntity entity2 = new ShootEntity();
        entity2.setEventID(3);
        entity2.setPlayID("1");
        entity2.setEventName("射门");
        entity2.setTeamID("1");
        entity2.setPlayerName1("王五");
        entity2.setNumberBack1("10");
        entity2.setBodyPartyName("脚尖");
        entity2.setDistanceName("20米");
        entity2.setBallTypeName("抽射");
        entity2.setIsOK("1");
        entity2.setBallXPos(400);
        entity2.setBallYPos(200);
        entity2.setFrameXPos(400);
        entity2.setFrameYPos(200);
        entity2.setGkXPos(400);
        entity2.setGkYPos(200);
        eventList.add(entity2);

        ShootEntity entitys1 = new ShootEntity();
        entitys1.setLineXpos(4000);
        entitys1.setLineYpos(2000);
        entitys1.setLineEventID(1);
        entitys1.setTeamID("1");
        pointEvent.add(entitys1);

        ShootEntity entitys2 = new ShootEntity();
        entitys2.setLineXpos(7000);
        entitys2.setLineYpos(2800);
        entitys2.setLineEventID(2);
        entitys2.setTeamID("2");
        pointEvent.add(entitys2);

        ShootEntity entitys3 = new ShootEntity();
        entitys3.setLineXpos(10000);
        entitys3.setLineYpos(3600);
        entitys3.setLineEventID(3);
        entitys3.setTeamID("1");
        pointEvent.add(entitys3);

        footline.setData(pointEvent, MainActivity.this);
        transformfoot.setData(pointEvent,eventList, MainActivity.this);

    }
}
