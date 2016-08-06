package pritam.com.studentofcharlotte.StorageUploads;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;

import pritam.com.studentofcharlotte.LoginActivity;
import pritam.com.studentofcharlotte.MainActivity;
import pritam.com.studentofcharlotte.R;

public class NewPost extends AppCompatActivity {
    int RESULT_LOAD_IMG = 100;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    Uri selectedImage;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        final Spinner interestSpinner = (Spinner) findViewById(R.id.interestListSpinner);
        final EditText postText = (EditText) findViewById(R.id.newAchievementEditText);

        //List<String>
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, MainActivity.studentInterestList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        interestSpinner.setAdapter(arrayAdapter);

        findViewById(R.id.loadGalleryButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
// Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);

            }
        });


        findViewById(R.id.uploadMyPost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("OnClick",interestSpinner.getSelectedItem().toString());
                if (postText.getText().toString().trim().length()>0){
                    databaseReference = FirebaseDatabase.getInstance().getReference();

                    final String key = databaseReference.child("interests").child(interestSpinner.getSelectedItem().toString())
                            .child("iAchievements").push().getKey();
                    final MyPost post = new MyPost(LoginActivity.studentName,postText.getText().toString().trim(),0,"http://dummy.url.com/fake",interestSpinner.getSelectedItem().toString(),key);
                    if (selectedImage != null){
                        StorageReference storageRef = storage.getReferenceFromUrl("gs://socialmobility-a540d.appspot.com");
                        storageRef.child("post/"+key).putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Log.d("URL is ",taskSnapshot.getDownloadUrl().toString());
                                post.setImageUrl(taskSnapshot.getDownloadUrl().toString());
                                databaseReference.child("interests").child(interestSpinner.getSelectedItem().toString())
                                        .child("iAchievements").child(key).setValue(post);
                                databaseReference.child("posts").child(key).child("interest").setValue(interestSpinner.getSelectedItem().toString());

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("Exception is - ",e.toString());
                            }
                        });


                    }
                    else {
                        databaseReference.child("interests").child(interestSpinner.getSelectedItem().toString())
                                .child("iAchievements").child(key).setValue(post);
                        databaseReference.child("posts").child(key).child("interest").setValue(interestSpinner.getSelectedItem().toString());

                    }
                    finish();
                }

            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Log.d("Image Load","slected image is "+selectedImage);
                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) findViewById(R.id.imageToUpload);
                // Set the Image in ImageView after decoding the String
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }
}
