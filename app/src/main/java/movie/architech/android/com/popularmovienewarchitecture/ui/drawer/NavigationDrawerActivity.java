package movie.architech.android.com.popularmovienewarchitecture.ui.drawer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import movie.architech.android.com.popularmovienewarchitecture.R;

public class NavigationDrawerActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        Toolbar toolbar = (Toolbar)findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);

        TextView textView = (TextView)findViewById(R.id.textView);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        NavigationView navView = (NavigationView)findViewById(R.id.nav_view);

        try {

        /*InputStream ins = getResources().openRawResource(R.raw.nasm);
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        int size = 0;
// Read the entire resource into a local byte buffer.
        byte[] buffer = new byte[1024];
        while((size=ins.read(buffer,0,1024))>=0){
            outputStream.write(buffer,0,size);
        }
        ins.close();
        buffer=outputStream.toByteArray();*/
//            InputStream ins = getResources().openRawResource(R.raw.nasm);

            InputStream is = getResources().openRawResource(R.raw.nasm);

            // We guarantee that the available method returns the total
            // size of the asset...  of course, this does mean that a single
            // asset can't be more than 2 gigs.
            int size = is.available();

            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // Convert the buffer into a string.
            String text = new String(buffer);

            // Finally stick the string into the text view
            textView.setText(text);
        } catch (IOException e) {
            // Should never happen!
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
