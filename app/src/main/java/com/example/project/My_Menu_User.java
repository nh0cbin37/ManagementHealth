package com.example.project;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

//public class My_Menu_User extends ArrayAdapter<ThucAn> {
//    ArrayList<ThucAn> arrMenu = new ArrayList<ThucAn>();
//    public My_Menu_User(@NonNull Context context, int resource, @NonNull ArrayList<ThucAn> objects) {
//        super(context, resource, objects);
//        this.arrMenu = objects;
//    }
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        View v = convertView;
//        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        v = inflater.inflate(R.layout.my_menu_user,null);
//        ImageView imgvie = (ImageView) v.findViewById(R.id.imgmonan);
//        TextView tenmon = (TextView) v.findViewById(R.id.tenmonan);
//
//        //set du lieu
////        String imgpath = arrMenu.get(position).getHinhAnh();
////        //String Viepath = "/data/data/com.example.project/file/gapbung.mp4";
////        Uri uri = Uri.parse(imgpath);
////        imgvie.setImageURI(uri);
//
//
//        tenmon.setText(arrMenu.get(position).getTenThucAn());
////        if(WorkoutlistIDusers.get(position).getStatus() == 1)
////        {
////            // tieps tuc
////            ImageView imgDone = (ImageView) v.findViewById(R.id.imageWOdoneormove);
////            String imgpath = "/data/data/com.example.project/file/logodonewo.png";
////            //String Viepath = "/data/data/com.example.project/file/gapbung.mp4";
////            Uri uriimgdone = Uri.parse(imgpath);
////            imgDone.setImageURI(uriimgdone);
////        }
//        return v;
////        return super.getView(position, convertView, parent);
//    }
//}

public class My_Menu_User extends RecyclerView.Adapter<My_Menu_User.SimpleViewHolder> {



    ArrayList<ThucAn> mylist = new ArrayList<ThucAn>();
    SQLiteDatabase db;
    public My_Menu_User(Context context, ArrayList<ThucAn> checklist) {

        db = context.openOrCreateDatabase(Login.DATABASE_NAME,Context.MODE_PRIVATE, null);
        mylist = checklist;
    }

    @Override
    public My_Menu_User.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_menu_user, parent, false);
        return new My_Menu_User.SimpleViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final My_Menu_User.SimpleViewHolder holder, final int position) {
        try {



            holder.name.setText(mylist.get(position).getTenThucAn());
            String imgpath = mylist.get(position).HinhAnh;
            //String Viepath = "/data/data/com.example.project/file/gapbung.mp4";
            Uri uriimgdone = Uri.parse(imgpath);
            holder.picture.setImageURI(uriimgdone);
            holder.soluong.setText(mylist.get(position).getSoluong()+"");
        }catch (Exception ex)
        {
            int t = 0;
        }


    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView picture;
        TextView soluong;


        public SimpleViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tenmonan);
            picture = (ImageView) itemView.findViewById(R.id.imgmonan);
            soluong = (TextView) itemView.findViewById(R.id.txtsoluong);

        }
    }
    public static class ItemOffsetDecoration extends RecyclerView.ItemDecoration {
        private static int mItemOffset;

        public  ItemOffsetDecoration(int itemOffset) {
            mItemOffset = itemOffset;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
        }
    }
}
