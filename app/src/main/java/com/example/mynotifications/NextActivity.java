package com.example.mynotifications;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;



public class NextActivity extends AppCompatActivity {
DatabaseReference reff;
Button btn;
    private FloatingActionButton add;
    private Dialog dialog;
    //  private AppDatabase appDatabase;
    private RecyclerView recyclerView;
    //  private AdapterReminders adapter;
    private List<CalendarContract.Reminders> temp;
    private TextView empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
    //Create database instance FirebaseDatabase
        reff= FirebaseDatabase.getInstance().getReference("device_tokens");

        reff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String tok  = dataSnapshot.getValue().toString();
                System.out.println("tok ------"+tok);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btn=(Button)findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                public static final MediaType JSON
                        = MediaType.parse("application/json; charset=utf-8");
                private void sendNotification(final String regToken) {
                    new AsyncTask<Void,Void,Void>(){
                        @Override
                        protected Void doInBackground(Void... params) {
                            try {
                                OkHttpClient client = new OkHttpClient();
                                JSONObject json=new JSONObject();
                                JSONObject dataJson=new JSONObject();
                                dataJson.put("body","Hi this is sent from device to device");
                                dataJson.put("title","dummy title");
                                json.put("data",dataJson);
                                json.put("to",regToken);
                                RequestBody body = RequestBody.create(JSON, json.toString());
                                Request request = new Request.Builder()
                                        .header("Authorization","key="+Constants.LEGACY_SERVER_KEY)
                                        .url("https://fcm.googleapis.com/fcm/send")
                                        .post(body)
                                        .build();
                                Response response = client.newCall(request).execute();
                                String finalResponse = response.body().string();
                            }catch (Exception e){
                                //Log.d(TAG,e+"");
                            }
                            return null;
                        }
                    }.execute();

                }

            }
        });











//        reff.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                String string=dataSnapshot.getValue();
//                System.out.println("token ------- "+dataSnapshot.getValue().toString());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

//    NotifierAlarm.mp
//
//    try{
//
////      Intent getter = getIntent();
////      MediaPlayer player = getter.getExtras();
//
//    } catch (Exception e){
//
//    }

//    appDatabase = AppDatabase.geAppdatabase(MainPage.this);

        add = findViewById(R.id.floatingButton);
//    empty = findViewById(R.id.empty);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReminder();
            }
        });

//    recyclerView = findViewById(R.id.recyclerView);
//    recyclerView.setHasFixedSize(true);
//    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainPage.this);
//    recyclerView.setLayoutManager(linearLayoutManager);
//    setItemsInRecyclerView();

    }

    public void addReminder(){

        dialog = new Dialog(NextActivity.this);
        dialog.setContentView(R.layout.floating_popup);

        final TextView textView = dialog.findViewById(R.id.date);
        Button select,add;
        select = dialog.findViewById(R.id.selectDate);
        add = dialog.findViewById(R.id.addButton);
        final EditText message = dialog.findViewById(R.id.message);


        final Calendar newCalender = Calendar.getInstance();
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(NextActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {

                        final Calendar newDate = Calendar.getInstance();
                        Calendar newTime = Calendar.getInstance();
                        TimePickerDialog time = new TimePickerDialog(NextActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                newDate.set(year,month,dayOfMonth,hourOfDay,minute,0);
                                Calendar tem = Calendar.getInstance();
                                Log.w("TIME",System.currentTimeMillis()+"");
                                if(newDate.getTimeInMillis()-tem.getTimeInMillis()>0)
                                    textView.setText(newDate.getTime().toString());
                                else
                                    Toast.makeText(NextActivity.this,"Invalid time",Toast.LENGTH_SHORT).show();

                            }
                        },newTime.get(Calendar.HOUR_OF_DAY),newTime.get(Calendar.MINUTE),true);
                        time.show();

                    }
                },newCalender.get(Calendar.YEAR),newCalender.get(Calendar.MONTH),newCalender.get(Calendar.DAY_OF_MONTH));

                dialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dialog.show();

            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//        RoomDAO roomDAO = appDatabase.getRoomDAO();
                Reminders reminders = new Reminders();
                reminders.setMessage(message.getText().toString().trim());
                Date remind = new Date(textView.getText().toString().trim());
                reminders.setRemindDate(remind);
//        roomDAO.Insert(reminders);
//        List<Reminders> l = roomDAO.getAll();
//        reminders = l.get(l.size()-1);
//        Log.e("ID chahiye",reminders.getId()+"");

                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
                calendar.setTime(remind);
                calendar.set(Calendar.SECOND,0);
                Intent intent = new Intent(NextActivity.this,NotifierAlarm.class);
                intent.putExtra("Message",reminders.getMessage());
                intent.putExtra("RemindDate",reminders.getRemindDate().toString());
                intent.putExtra("id",reminders.getId());
                PendingIntent intent1 = PendingIntent.getBroadcast(NextActivity.this,reminders.getId(),intent,PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),intent1);

                Toast.makeText(NextActivity.this,"Inserted Successfully",Toast.LENGTH_SHORT).show();
                setItemsInRecyclerView();
//        AppDatabase.destroyInstance();
                dialog.dismiss();

            }
        });


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

    public void setItemsInRecyclerView(){

//    RoomDAO dao = appDatabase.getRoomDAO();
//    temp = dao.orderThetable();
//    if(temp.size()>0) {
//      empty.setVisibility(View.INVISIBLE);
//      recyclerView.setVisibility(View.VISIBLE);
//    }
//    adapter = new AdapterReminders(temp);
//    recyclerView.setAdapter(adapter);

    }

    public void stop_alarm(View view) {
        Player.getInstance(getApplicationContext().getApplicationContext()).stopMusic();

    }
}
