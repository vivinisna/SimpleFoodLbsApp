package dlmbg.pckg.wisata.kuliner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * This shows how to listen to some {@link GoogleMap} events.
 */
public class DetailMakanan extends FragmentActivity {

	private Long id;
	
    private GoogleMap mMap;
	private ImageView mImageView;	
	String var_posisi,var_nama_tempat,var_nama_makanan,var_harga,var_gambar;
    public LatLng SAVED_POSITION = new LatLng(3.584695,98.675079);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_makanan);

		mImageView = (ImageView) findViewById(R.id.detail_pict);
		
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras != null && extras.containsKey(DashboardActivity.EXTRA_ROWID)) {
				id = extras.getLong(DashboardActivity.EXTRA_ROWID);
			}
			else
			{
				var_nama_tempat = extras.getString("nama_tempat");
				var_posisi = extras.getString("lat_lang");
				var_nama_makanan = extras.getString("nama_makanan");
				var_harga = extras.getString("harga");
				var_gambar = extras.getString("gambar");
				
				String replace_string_first = var_posisi.replace("lat/lng: (", "");
				String replace_string_second = replace_string_first.replace(")", "");
				
				String[] split_var = replace_string_second.split(",");

				double lat = Double.parseDouble(split_var[0]);
				double lang = Double.parseDouble(split_var[1]);
				SAVED_POSITION = new LatLng(lat,lang);
			}
		}
		
		Bitmap bitmap = BitmapFactory.decodeFile(var_gambar);
		mImageView.setImageBitmap(bitmap);

        setUpMapIfNeeded();
    }
    
    @Override
	protected void onDestroy() {
		super.onDestroy();
	}

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SAVED_POSITION, 15));
	    mMap.addMarker(new MarkerOptions()
	    .position(SAVED_POSITION)
        .title(var_nama_tempat)
        .snippet("Nama Makanan : "+var_nama_makanan+" - Harga : "+var_harga));
    }
}