package com.example.customnavicationdrawer.fragment;

import static com.example.customnavicationdrawer.MainActivity.MY_REQUEST_CODE;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.customnavicationdrawer.MainActivity;
import com.example.customnavicationdrawer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyProfileFragment extends Fragment {

    private View mView;
    private ImageView imgAvatar;
    private EditText edtFullName, edtEmail;
    private Button btnUpdateProfile;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView =  inflater.inflate(R.layout.fragment_my_profile,container,false);

        
        initUi();
        setUserInfomation();
        initListener();
        return mView;
    }
    private void initUi(){
        imgAvatar = mView.findViewById(R.id.img_avatar);
        edtFullName = mView.findViewById(R.id.edt_full_name);
        edtEmail = mView.findViewById(R.id.edt_email);
        btnUpdateProfile = mView.findViewById(R.id.btn_update_profile);
    }
    private void setUserInfomation() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            return;
        }
        edtFullName.setText(user.getDisplayName());
        edtEmail.setText(user.getEmail());
        Glide.with(getActivity()).load(user.getPhotoUrl()).error(R.drawable.ic_avatar_default).into(imgAvatar);
    }
    private void initListener(){
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });
    }

    private void onClickRequestPermission(){
        MainActivity mainActivity = (MainActivity) getActivity();
        if(mainActivity == null){
            return;
        }
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            mainActivity.openGallery();
            return;
        }

        if(getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            mainActivity.openGallery();
        }else{
            String [] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            getActivity().requestPermissions(permissions,MY_REQUEST_CODE);
        }
    }
    public void setBitmapImageView(Bitmap bitmapImageView){
        imgAvatar.setImageBitmap(bitmapImageView);
    }
}
