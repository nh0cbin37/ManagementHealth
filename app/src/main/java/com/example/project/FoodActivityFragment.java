package com.example.project;

import static android.content.Context.MODE_PRIVATE;

import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link FoodActivityFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class FoodActivityFragment extends Fragment {


    public static final int SAVE_Item = 113;
    public static final int OPENSET_FOOD = 114;
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public FoodActivityFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment FoodActivityFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static FoodActivityFragment newInstance(String param1, String param2) {
//        FoodActivityFragment fragment = new FoodActivityFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
    RecyclerView lsMenuBuoiSang;
    RecyclerView lsMenuBuoiTrua;
    RecyclerView lsMenuBuoiTrua2;
    RecyclerView lsMenuBuoiChieu;
    ArrayList<ThucAn> listmenusang = new ArrayList<ThucAn>();
    ArrayList<ThucAn> listmenutrua = new ArrayList<ThucAn>();
    ArrayList<ThucAn> listmenutrua2 = new ArrayList<ThucAn>();
    ArrayList<ThucAn> listmenuchieu = new ArrayList<ThucAn>();
    My_Menu_User arrayadapter;
    My_Menu_User arrayadaptertrua;
    My_Menu_User arrayadaptertrua2;
    My_Menu_User arrayadapterchieu;
    ArrayList<ThucAn> listtraicay = new ArrayList<ThucAn>();
    My_Menu_User arrtraicay;
    RecyclerView lstvtraicay;
    SQLiteDatabase db;
    Date date = new Date();
    String currentTime = new SimpleDateFormat("yyyy-MM-dd").format(date);
    int []luu = new int[12];
    int []luuidmenu = new int[4];
    public static int countluu = 0;

    int[]stt = new int[12];
private void getMenuBuoiSang() {
    try {
        //Studentlist.add(new Room(""));

        db = getActivity().openOrCreateDatabase(Login.DATABASE_NAME, MODE_PRIVATE, null);
        Cursor ctrua= db.rawQuery("SELECT THUCAN.*,MENUItem.Id_Menu,MENUItem.STT from THUCAN,THUCDONTUAN,USER,MENU,MENUItem \n" +
                "where THUCAN.Id_food = MENUItem.Id_food and MENU.Id_Menu = MENUItem.Id_Menu and MENU.Id_User=USER.Id_User and MENU.Id_Menu = THUCDONTUAN.Id_Menu and MENU.Id_User = ? and THUCDONTUAN.Buoi = ? and NgaySudung = ? ",new String[]{Login.ID_U+"",1+"",currentTime+""},null);
        ctrua.moveToFirst();
        listmenusang = new ArrayList<ThucAn>();
        luuidmenu[0]= ctrua.getInt(18);

        while(!ctrua.isAfterLast() )
        {
            listmenusang.add( new ThucAn(ctrua.getString(1)+"",ctrua.getString(2)+"",ctrua.getInt(3),ctrua.getDouble(4),ctrua.getDouble(5),ctrua.getDouble(6),ctrua.getDouble(7),1,ctrua.getString(11)+"",ctrua.getString(12),MainActivity.LoaiBMI));
            luu[countluu]= ctrua.getInt(0);
            stt[countluu++] = ctrua.getInt(19);
            ctrua.moveToNext();
        }

        arrayadapter = new My_Menu_User(getContext(),listmenusang);
        lsMenuBuoiSang.setAdapter(arrayadapter);
        LinearLayoutManager layoutManager1= new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        lsMenuBuoiSang.setLayoutManager(layoutManager1);

        My_Menu_User.ItemOffsetDecoration itemDecoration = new My_Menu_User.ItemOffsetDecoration(5);
        lsMenuBuoiSang.addItemDecoration(itemDecoration);

    } catch (Exception ex) {
        Toast.makeText(getActivity(), "Lỗi1", Toast.LENGTH_SHORT).show();

    }
}


    private void getMenuBuoiTrua() {
        try {

            db = getActivity().openOrCreateDatabase(Login.DATABASE_NAME, MODE_PRIVATE, null);
            Cursor buoitrua= db.rawQuery("SELECT THUCAN.* ,MENUItem.Id_Menu,MENUItem.STT from THUCAN,THUCDONTUAN,USER,MENU,MENUItem where THUCAN.Id_food = MENUItem.Id_food and MENU.Id_Menu = MENUItem.Id_Menu and MENU.Id_User=USER.Id_User and MENU.Id_Menu = THUCDONTUAN.Id_Menu and MENU.Id_User = ? and MENU.Buoi = ? and NgaySudung = ?   ",new String[]{Login.ID_U+"",2+"",currentTime+""},null);
            buoitrua.moveToFirst();
            listmenutrua = new ArrayList<ThucAn>();
            luuidmenu[1]= buoitrua.getInt(18);

            while(!buoitrua.isAfterLast() )
            {
                listmenutrua.add( new ThucAn(buoitrua.getInt(0),buoitrua.getString(1)+"",buoitrua.getString(2)+"",buoitrua.getInt(3),buoitrua.getDouble(4),buoitrua.getDouble(5),buoitrua.getDouble(6),buoitrua.getDouble(7),2,buoitrua.getString(11)+"",buoitrua.getString(12),MainActivity.LoaiBMI+""));
                luu[countluu]= buoitrua.getInt(0);
                stt[countluu++] = buoitrua.getInt(19);
                buoitrua.moveToNext();

            }

            arrayadaptertrua = new My_Menu_User(getContext(),listmenutrua);
            lsMenuBuoiTrua.setAdapter(arrayadaptertrua);
            LinearLayoutManager GridoutManager2= new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
            lsMenuBuoiTrua.setLayoutManager(GridoutManager2);
            My_Menu_User.ItemOffsetDecoration itemDecoration = new My_Menu_User.ItemOffsetDecoration(5);
            lsMenuBuoiTrua.addItemDecoration(itemDecoration);

        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Lỗi2", Toast.LENGTH_SHORT).show();

        }
    }
    private void getMenuBuoiChieu() {
        try {
            //Studentlist.add(new Room(""));

            db = getActivity().openOrCreateDatabase(Login.DATABASE_NAME, MODE_PRIVATE, null);
            Cursor ctrua= db.rawQuery("SELECT THUCAN.*,MENUItem.Id_Menu,MENUItem.STT from THUCAN,THUCDONTUAN,USER,MENU,MENUItem \n" +
                    "where THUCAN.Id_food = MENUItem.Id_food and MENU.Id_Menu = MENUItem.Id_Menu and MENU.Id_User=USER.Id_User and MENU.Id_Menu = THUCDONTUAN.Id_Menu and MENU.Id_User = ? and THUCDONTUAN.Buoi = ? and NgaySudung = ? limit 3",new String[]{Login.ID_U+"",3+"",currentTime+""},null);
            ctrua.moveToFirst();
            listmenuchieu = new ArrayList<ThucAn>();
            luuidmenu[2]= ctrua.getInt(18);

            while(!ctrua.isAfterLast())
            {
                listmenuchieu.add( new ThucAn(ctrua.getInt(0),ctrua.getString(1)+"",ctrua.getString(2)+"",ctrua.getInt(3),ctrua.getDouble(4),ctrua.getDouble(5),ctrua.getDouble(6),ctrua.getDouble(7),3,ctrua.getString(11)+"",ctrua.getString(12),MainActivity.LoaiBMI));
                luu[countluu]= ctrua.getInt(0);
                stt[countluu++] = ctrua.getInt(19);
                ctrua.moveToNext();
            }

            arrayadapterchieu = new My_Menu_User(getContext(),listmenuchieu);
            lsMenuBuoiChieu.setAdapter(arrayadapterchieu);
            LinearLayoutManager layoutManager1= new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
            lsMenuBuoiChieu.setLayoutManager(layoutManager1);

            My_Menu_User.ItemOffsetDecoration itemDecoration = new My_Menu_User.ItemOffsetDecoration(5);
            lsMenuBuoiChieu.addItemDecoration(itemDecoration);

        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Lỗi3", Toast.LENGTH_SHORT).show();

        }
    }

    private void insetTraicay()
    {
        db = getActivity().openOrCreateDatabase(Login.DATABASE_NAME, MODE_PRIVATE, null);
        Cursor ctrua= db.rawQuery("SELECT THUCAN.*,MENUItem.Id_Menu,MENUItem.STT from THUCAN,THUCDONTUAN,USER,MENU,MENUItem \n" +
                "where THUCAN.Id_food = MENUItem.Id_food and MENU.Id_Menu = MENUItem.Id_Menu and MENU.Id_User=USER.Id_User and MENU.Id_Menu = THUCDONTUAN.Id_Menu and MENU.Id_User = ? and THUCDONTUAN.Buoi = ? and NgaySudung = ? limit 3",new String[]{MainActivity.ID_USER+"",4+"",currentTime+""},null);
        ctrua.moveToFirst();
        listtraicay = new ArrayList<ThucAn>();
        luuidmenu[3]= ctrua.getInt(18);

            while(!ctrua.isAfterLast())
            {
                listtraicay.add( new ThucAn(ctrua.getInt(0),ctrua.getString(1)+"",ctrua.getString(2)+"",ctrua.getInt(3),ctrua.getDouble(4),ctrua.getDouble(5),ctrua.getDouble(6),ctrua.getDouble(7),4,ctrua.getString(11)+"",ctrua.getString(12),MainActivity.LoaiBMI));
                luu[countluu]= ctrua.getInt(0);
                stt[countluu++] = ctrua.getInt(19);
                ctrua.moveToNext();
            }
            arrtraicay = new My_Menu_User(getContext(),listtraicay);
            lstvtraicay.setAdapter(arrtraicay);
            LinearLayoutManager layoutManager1= new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
            lstvtraicay.setLayoutManager(layoutManager1);

            My_Menu_User.ItemOffsetDecoration itemDecoration = new My_Menu_User.ItemOffsetDecoration(5);
            lstvtraicay.addItemDecoration(itemDecoration);



    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        int id_food= -1;
        int j = 0;
        int block = 0;
        String soluong = "";
        try {
            switch (requestCode) {
                case OPENSET_FOOD:
                    if (resultCode == SAVE_Item) {
                        countluu=0;
                        Bundle bundle = data.getBundleExtra("data");
                        ThucAn[] tenthucanmoi = (ThucAn[]) bundle.getSerializable("id_foodnew");
                        int i = 0;
                        Cursor buoisang= db.rawQuery("SELECT THUCAN.*,MENUItem.Id_Menu from THUCAN,THUCDONTUAN,USER,MENU,MENUItem \n" +
                                "where THUCAN.Id_food = MENUItem.Id_food and MENU.Id_Menu = MENUItem.Id_Menu and MENU.Id_User=USER.Id_User and MENU.Id_Menu = THUCDONTUAN.Id_Menu and MENU.Id_User = ? and THUCDONTUAN.Buoi = ? and NgaySudung = ? ",new String[]{Login.ID_U+"",1+"",currentTime+""},null);
                        buoisang.moveToFirst();
                        Cursor buoitrua= db.rawQuery("SELECT THUCAN.* ,MENUItem.Id_Menu from THUCAN,THUCDONTUAN,USER,MENU,MENUItem where THUCAN.Id_food = MENUItem.Id_food and MENU.Id_Menu = MENUItem.Id_Menu and MENU.Id_User=USER.Id_User and MENU.Id_Menu = THUCDONTUAN.Id_Menu and MENU.Id_User = ? and MENU.Buoi = ? and NgaySudung = ?   ",new String[]{Login.ID_U+"",2+"",currentTime+""},null);
                        buoitrua.moveToFirst();
                        Cursor buoichieu= db.rawQuery("SELECT THUCAN.*,MENUItem.Id_Menu from THUCAN,THUCDONTUAN,USER,MENU,MENUItem \n" +
                                "where THUCAN.Id_food = MENUItem.Id_food and MENU.Id_Menu = MENUItem.Id_Menu and MENU.Id_User=USER.Id_User and MENU.Id_Menu = THUCDONTUAN.Id_Menu and MENU.Id_User = ? and THUCDONTUAN.Buoi = ? and NgaySudung = ? limit 3",new String[]{Login.ID_U+"",3+"",currentTime+""},null);
                        buoichieu.moveToFirst();
                        Cursor traicay= db.rawQuery("SELECT THUCAN.*,MENUItem.Id_Menu from THUCAN,THUCDONTUAN,USER,MENU,MENUItem \n" +
                                "where THUCAN.Id_food = MENUItem.Id_food and MENU.Id_Menu = MENUItem.Id_Menu and MENU.Id_User=USER.Id_User and MENU.Id_Menu = THUCDONTUAN.Id_Menu and MENU.Id_User = ? and THUCDONTUAN.Buoi = ? and NgaySudung = ? limit 3",new String[]{MainActivity.ID_USER+"",4+"",currentTime+""},null);
                       traicay.moveToFirst();
                        while (j < 12)
                        {

                            if(j<3)
                            {
                                listmenusang.set(i,tenthucanmoi[j]);
                                ContentValues values = new ContentValues();
                                values.put("Id_food",tenthucanmoi[j].getId_food()+"");
                                values.put("SoLuong",tenthucanmoi[j].getSoluong()+"");
                                db.update("MENUItem",values,"Id_Menu = ? and Id_food=? and STT=?", new String[]{buoisang.getInt(18)+"",buoisang.getInt(0)+"",stt[j]+""});
                                buoisang.moveToNext();
                                if(i==2){ arrayadapter.notifyDataSetChanged(); i = -1;}

                            }
                            else if(j < 6)
                            {
                                listmenutrua.set(i,tenthucanmoi[j]);
                                ContentValues values = new ContentValues();
                                values.put("Id_food",tenthucanmoi[j].getId_food()+"");
                                values.put("SoLuong",tenthucanmoi[j].getSoluong());
                                db.update("MENUItem",values,"Id_Menu = ? and Id_food=? and STT=?", new String[]{buoitrua.getInt(18)+"",buoitrua.getInt(0)+"",stt[j]+""});
                                buoitrua.moveToNext();
                                if(i==2){ arrayadaptertrua.notifyDataSetChanged(); i = -1;}
                            }else if(j < 9)
                            {
                                listmenuchieu.set(i,tenthucanmoi[j]);
                                ContentValues values = new ContentValues();
                                values.put("Id_food",tenthucanmoi[j].getId_food()+"");
                                values.put("SoLuong",tenthucanmoi[j].getSoluong());
                                db.update("MENUItem",values,"Id_Menu = ? and Id_food=? and STT=?", new String[]{buoichieu.getInt(18)+"",buoichieu.getInt(0)+"",stt[j]+""});
                                buoichieu.moveToNext();
                                if(i==2){ arrayadapterchieu.notifyDataSetChanged(); i = -1;}
                            }else {
                                listtraicay.set(i,tenthucanmoi[j]);
                                ContentValues values = new ContentValues();
                                values.put("Id_food",tenthucanmoi[j].getId_food()+"");
                                values.put("SoLuong",tenthucanmoi[j].getSoluong());
                                db.update("MENUItem",values,"Id_Menu = ? and Id_food=? and STT=?", new String[]{traicay.getInt(18)+"",traicay.getInt(0)+"",stt[j]+""});
                                traicay.moveToNext();
                                if(i==2){ arrtraicay.notifyDataSetChanged(); i = -1;}
                            }
                            j++;
                            i++;
                        }
//                        for (int i = 0; i < tenthucanmoi.length; i++) {
//
//                            if(block ==3){  block = 0; j++;}
//                            Cursor c = db.rawQuery("Select Id_food,SoLuong from THUCAN where TenThucAn = ? ", new String[]{tenthucanmoi[i]}, null);
//                            c.moveToFirst();
//                            ContentValues newValues = new ContentValues();
//                             id_food = c.getInt(0);
//                             soluong = c.getString(1);
//                            newValues.put("Id_food", id_food);
//                            newValues.put("SoLuong",soluong);
//                            //Lôi ở đây
//                            db.update("MENUItem", newValues, "Id_Menu=? and Id_food=?", new String[]{luuidmenu[j] + "", luu[i] + ""});
//                            block++;
//                        }
//                        getMenuBuoiSangofUpdate();
//                        getMenuBuoiTruaofUpdate();
//                        getMenuBuoiChieuofUpdate();
//                        insetTraicayofUpdate();
                    }
                    break;
            }
        }catch (Exception ex)
        {
            Toast.makeText(getContext(), "Lỗi insert", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    View rootview =  inflater.inflate(R.layout.fragment_food_activity, container, false);
        // Inflate the layout for this fragment

        try {

            ImageButton imgbtncreate = (ImageButton)rootview.findViewById(R.id.imgSettingFood);
            TextView txtcreate = (TextView)rootview.findViewById(R.id.txttaomoi);
            LinearLayout linearsetFood = (LinearLayout)rootview.findViewById(R.id.layoutlnsettingFood);
            linearsetFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(getContext(),CreateMenu.class);
                    bundle.putIntArray("chuoimonan",luu);
                    bundle.putIntArray("chuoiluidcacbuoi",luuidmenu);
                    intent.putExtra("data",bundle);
                    startActivityForResult(intent,OPENSET_FOOD);
                }
            });
            txtcreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(getContext(),CreateMenu.class);
                    bundle.putIntArray("chuoimonan",luu);
                    bundle.putIntArray("chuoiluidcacbuoi",luuidmenu);
                    intent.putExtra("data",bundle);
                    startActivityForResult(intent,OPENSET_FOOD);
                }
            });
            imgbtncreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(getContext(),CreateMenu.class);
                    bundle.putIntArray("chuoimonan",luu);
                    bundle.putIntArray("chuoiluidcacbuoi",luuidmenu);
                    intent.putExtra("data",bundle);
                    startActivityForResult(intent,OPENSET_FOOD);
                }
            });
            lsMenuBuoiSang = (RecyclerView) rootview.findViewById(R.id.lstviemenuuser);
            lsMenuBuoiTrua = (RecyclerView) rootview.findViewById(R.id.recycbuoichieu1);
            //  lsMenuBuoiTrua2 = (RecyclerView)rootview.findViewById(R.id.recycbuoitrua2);
            lsMenuBuoiChieu = (RecyclerView) rootview.findViewById(R.id.recycbuoichieu);
            lstvtraicay = (RecyclerView) rootview.findViewById(R.id.recytraicay);
            getMenuBuoiSang();
            getMenuBuoiTrua();
            //getMenuBuoiTrua2();
            getMenuBuoiChieu();
            insetTraicay();
            countluu = 0;
            return rootview;
        }catch (Exception ex)
        {
            Toast.makeText(getContext(), "Lỗi4", Toast.LENGTH_SHORT).show();
        }
        return rootview;
    }
}