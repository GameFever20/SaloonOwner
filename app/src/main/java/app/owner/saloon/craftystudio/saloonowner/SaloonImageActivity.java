package app.owner.saloon.craftystudio.saloonowner;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import utils.FireBaseHandler;
import utils.Saloon;

public class SaloonImageActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{

    private static final int SELECT_PICTURE = 0;
    private static final int SELECT_IMAGE_1 = 1;
    private static final int REQUEST_EXTRA_IMAGE = 10;
    private static final int REQUEST_PROFILE_IMAGE = 15;


    Uri profileImageUri, image1Uri, image2Uri, image3Uri;
    String saloonUID = "abc";
    
    int statusProfileUpload ,statusImage1Upload ,statusImage2Upload , statusImage3Upload;
    private final int RC_CAMERA_AND_LOCATION=10;


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
                //uploadImageToFireBase();
            }
        });


        final ImageView imageView = (ImageView) findViewById(R.id.saloonimage_showcase_imageView);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        StorageReference storageReference = storageRef.child("saloon_image/" + saloonUID + "/" + "profile_image");


        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(storageReference)
                .thumbnail(0.5f)
                .override(900, 400)
                .crossFade(100)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView);


        ImageView imageView1 = (ImageView)findViewById(R.id.saloonimage_image1_imageView);
         storageReference = storageRef.child("saloon_image/" + saloonUID + "/" + "image_1");


        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(storageReference)
                .thumbnail(0.5f)
                .override(900, 400)
                .crossFade(100)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView1);

         imageView1 = (ImageView)findViewById(R.id.saloonimage_image2_imageView);
        storageReference = storageRef.child("saloon_image/" + saloonUID + "/" + "image_2");


        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(storageReference)
                .thumbnail(0.5f)
                .override(900, 400)
                .crossFade(100)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView1);


         imageView1 = (ImageView)findViewById(R.id.saloonimage_image3_imageView);
        storageReference = storageRef.child("saloon_image/" + saloonUID + "/" + "image_3");


        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(storageReference)
                .thumbnail(0.5f)
                .override(900, 400)
                .crossFade(100)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView1);


        methodRequiresTwoPermission();


    }

    public void addShowcasePhotoClick(View view) {


        Matisse.from(SaloonImageActivity.this)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(1)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_PROFILE_IMAGE);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                //selectedImagePath = getPath(selectedImageUri);

                ImageView imageView = (ImageView) findViewById(R.id.saloonimage_showcase_imageView);
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


            } else if (requestCode == SELECT_IMAGE_1) {

                Uri selectedImageUri = data.getData();
                //selectedImagePath = getPath(selectedImageUri);

                ImageView imageView = (ImageView) findViewById(R.id.saloonimage_image1_imageView);
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

            } else if (requestCode == REQUEST_EXTRA_IMAGE) {
                List<Uri> mSelected = Matisse.obtainResult(data);
                Log.d("Matisse", "mSelected: " + mSelected);
                if (mSelected.size() >= 1) {
                    image1Uri =mSelected.get(0);
                    ImageView imageView = (ImageView) findViewById(R.id.saloonimage_image1_imageView);
                    imageView.setImageURI(image1Uri);
                }
                if (mSelected.size() >= 2) {
                    image2Uri= mSelected.get(1);
                    ImageView imageView = (ImageView) findViewById(R.id.saloonimage_image2_imageView);
                    imageView.setImageURI(image2Uri);
                }
                if (mSelected.size() >= 3) {
                    image3Uri = mSelected.get(2);
                    ImageView imageView = (ImageView) findViewById(R.id.saloonimage_image3_imageView);
                    imageView.setImageURI(image3Uri);
                }


            } else if (requestCode == REQUEST_PROFILE_IMAGE) {

                List<Uri> mSelectedProfileImage = Matisse.obtainResult(data);
                profileImageUri = mSelectedProfileImage.get(0);
                Log.d("Matisse", "mSelected: " + profileImageUri);
                ImageView imageView = (ImageView) findViewById(R.id.saloonimage_showcase_imageView);
                imageView.setImageURI(profileImageUri);


            }
        }
    }

    public void addImage1PhotoClick(View view) {

        // in onCreate or any event where your want the user to
        // select a file
        Matisse.from(SaloonImageActivity.this)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(3)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_EXTRA_IMAGE);

    }

    public void selectImageFromGallery(View view) {



        Matisse.from(SaloonImageActivity.this)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(3)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_EXTRA_IMAGE);

    }

    public void uploadImageToFireBase(View view) {
        final Saloon saloon = MainActivity.SALOON;

        if (saloon.getSaloonPoint() == 0) {
            return;
        } else if (saloon.getSaloonPoint() == -1) {
            if (profileImageUri != null) {
                saloon.setSaloonPoint(10);
            }
        }

        if (saloon.getSaloonImageList() == null) {
            HashMap<String, String> imageList = new HashMap<String, String>();

            saloon.setSaloonImageList(imageList);

        }

        final FireBaseHandler fireBaseHandler = new FireBaseHandler();

        if (profileImageUri != null) {


            statusProfileUpload =1;

            fireBaseHandler.uploadSaloonShwcaseImage(profileImageUri, saloonUID, "profile_image", new FireBaseHandler.OnSaloonImageListner() {
                @Override
                public void onImageDownLoaded(boolean isSucessful, File imageFile) {

                }

                @Override
                public void onImageUploaded(boolean isSucessful, Uri downloadUri) {

                    Toast.makeText(SaloonImageActivity.this, "Profile image uploaded", Toast.LENGTH_SHORT).show();
                    saloon.getSaloonImageList().put("profile_image", downloadUri.toString());
                    statusProfileUpload=2 ;
                    checkForProgress(fireBaseHandler);

                }
            });

        }
        if (image1Uri != null) {


            statusImage1Upload =1;
            fireBaseHandler.uploadSaloonShwcaseImage(image1Uri, saloonUID, "image_1", new FireBaseHandler.OnSaloonImageListner() {
                @Override
                public void onImageDownLoaded(boolean isSucessful, File imageFile) {

                }

                @Override
                public void onImageUploaded(boolean isSucessful, Uri downloadUri) {

                    Toast.makeText(SaloonImageActivity.this, "image 1 uploaded", Toast.LENGTH_SHORT).show();
                    saloon.getSaloonImageList().put("image_1", downloadUri.toString());
                    Log.d("path" , downloadUri.toString());
                    statusImage1Upload =2;
                    checkForProgress(fireBaseHandler);

                }
            });
        }
        if (image2Uri != null) {

            statusImage2Upload = 1;
            fireBaseHandler.uploadSaloonShwcaseImage(image2Uri, saloonUID, "image_2", new FireBaseHandler.OnSaloonImageListner() {
                @Override
                public void onImageDownLoaded(boolean isSucessful, File imageFile) {

                }

                @Override
                public void onImageUploaded(boolean isSucessful, Uri downloadUri) {

                    Toast.makeText(SaloonImageActivity.this, " image 2 uploaded", Toast.LENGTH_SHORT).show();
                    saloon.getSaloonImageList().put("image_2", downloadUri.toString());
                    statusImage2Upload=2;
                    checkForProgress(fireBaseHandler);
                }
            });

        }
        if (image3Uri != null) {

            statusImage3Upload =1;
            fireBaseHandler.uploadSaloonShwcaseImage(image3Uri, saloonUID, "image_3", new FireBaseHandler.OnSaloonImageListner() {
                @Override
                public void onImageDownLoaded(boolean isSucessful, File imageFile) {

                }

                @Override
                public void onImageUploaded(boolean isSucessful, Uri downloadUri) {

                    Toast.makeText(SaloonImageActivity.this, " image 3 uploaded", Toast.LENGTH_SHORT).show();
                    saloon.getSaloonImageList().put("image_3", downloadUri.toString());
                    statusImage3Upload =2;
                    checkForProgress(fireBaseHandler);
                }
            });

        }


      
        



    }

    private void checkForProgress(FireBaseHandler fireBaseHandler) {
        if(!(statusProfileUpload == 1 || statusImage1Upload ==1 || statusImage2Upload==1 ||statusImage3Upload==1)){
            Toast.makeText(this, "Upload saloon", Toast.LENGTH_SHORT).show();

            fireBaseHandler.uploadSaloon(saloonUID, MainActivity.SALOON, new FireBaseHandler.OnSaloonDownload() {
                @Override
                public void onSaloon(Saloon saloon) {

                }

                @Override
                public void onSaloonValueUploaded(boolean isSucessful) {

                    Toast.makeText(SaloonImageActivity.this, "Uploaded images ", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }






    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            // ...
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.camera_and_location_rationale),
                    RC_CAMERA_AND_LOCATION, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {


    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }
}
