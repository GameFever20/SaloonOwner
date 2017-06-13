package app.owner.saloon.craftystudio.saloonowner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import utils.FireBaseHandler;

public class SaloonImageActivity extends AppCompatActivity {

    private static final int SELECT_PICTURE = 0;
    private static final int SELECT_IMAGE_1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saloon_image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        final ImageView imageView = (ImageView)findViewById(R.id.saloonimage_showcase_imageView);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        StorageReference storageReference = storageRef.child("saloon_image/" + "abc" + "/" + "profile_image");





        Glide.with(this )
                .using(new FirebaseImageLoader())
                .load(storageReference)
                .thumbnail(0.5f)
                .override(900, 400)
                .crossFade(100)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView);



      /*  try {
            Glide.with(this)
                    .load(storageReference)
                    .thumbnail(0.5f)
                    .override(900, 400)
                    .crossFade(100)
                    .fitCenter()
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            try {
                                imageView.setImageDrawable(resource);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
*/

    }

    public void addShowcasePhotoClick(View view) {

        // in onCreate or any event where your want the user to
        // select a file
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                //selectedImagePath = getPath(selectedImageUri);

                ImageView imageView = (ImageView)findViewById(R.id.saloonimage_showcase_imageView);
                imageView.setImageURI(selectedImageUri);

                FireBaseHandler fireBaseHandler = new FireBaseHandler();
                fireBaseHandler.uploadSaloonShwcaseImage(selectedImageUri, "abc", "profile_image", new FireBaseHandler.OnSaloonImageListner() {
                    @Override
                    public void onImageDownLoaded(boolean isSucessful, File imageFile) {
                        Toast.makeText(SaloonImageActivity.this, "Image uploaded", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onImageUploaded(boolean isSucessful, Uri downloadUri) {
                        Toast.makeText(SaloonImageActivity.this, "Image uploaded", Toast.LENGTH_SHORT).show();

                    }
                });


            }else if(requestCode == SELECT_IMAGE_1) {

                Uri selectedImageUri = data.getData();
                //selectedImagePath = getPath(selectedImageUri);

                ImageView imageView = (ImageView)findViewById(R.id.saloonimage_image1_imageView);
                imageView.setImageURI(selectedImageUri);

                FireBaseHandler fireBaseHandler = new FireBaseHandler();
                fireBaseHandler.uploadSaloonShwcaseImage(selectedImageUri, "abc", "image_1", new FireBaseHandler.OnSaloonImageListner() {
                    @Override
                    public void onImageDownLoaded(boolean isSucessful, File imageFile) {
                        Toast.makeText(SaloonImageActivity.this, "Image uploaded", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onImageUploaded(boolean isSucessful, Uri downloadUri) {
                        Toast.makeText(SaloonImageActivity.this, "Image uploaded", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        }
    }

    public void addImage1PhotoClick(View view) {

        // in onCreate or any event where your want the user to
        // select a file
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_IMAGE_1);

    }
}
