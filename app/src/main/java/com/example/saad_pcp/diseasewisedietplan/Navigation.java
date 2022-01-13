package com.example.saad_pcp.diseasewisedietplan;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//yahan pe code aye ga
        getSupportFragmentManager().beginTransaction().replace(
                R.id.rel_lyout_for_frags,
                new UserPlan()
        ).commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.rel_lyout_for_frags,
                    new DiseaseSetting()
            ).commit();



            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Create_plan) {
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.rel_lyout_for_frags,
                    new CreatePlanFragment()
            ).commit();


            // Inflate the layout for this fragment


        } else if (id == R.id.Saved_plan) {
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.rel_lyout_for_frags,
                    new SavePlanFragment()
            ).commit();


        } else if (id == R.id.Suggest_plan) {

        }
        else if(id==R.id.nav_AcPlan){
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.rel_lyout_for_frags,
                    new UserPlan()
            ).commit();
        }
       /* else if(id==R.id.Edit_Disease){
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.rel_lyout_for_frags,
                    new EditDiseaseFragment()
            ).commit();
        }*/
        else if(id==R.id.Add_Disease){
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.rel_lyout_for_frags,
                    new AddDiseaseFragment()
            ).commit();

        }
        else if(id==R.id.Add_Food){
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.rel_lyout_for_frags,
                    new AddFoodFragment()
            ).commit();


        }
        else if(id==R.id.nav_manage){
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.rel_lyout_for_frags,
                    new DiseaseSetting()
            ).commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    /*
    public static class EditDiseaseFragment extends Fragment implements
            FoodMultiSelector.FoodMultiSpinnerListener{

        @Override
        public void onItemsSelected(boolean[] selected) {

        }

        public EditDiseaseFragment() {
            // Required empty public constructor
        }
        private ArrayList<FoodProduct> getProductos(String s){
//String st={"AIDS","FEVER","HEAD ACH","Blood Presure","Kidney","Sugar","Maleria","AIDS","FEVER","HEAD ACH","Blood Presure","Kidney","Sugar","Maleria","",""};
            DataBaseHelper dataBaseHelper=new DataBaseHelper(getContext());

            ArrayList<FoodProduct> arrayList = dataBaseHelper.get_FoodWithDisease(s);

              Cursor res=  dataBaseHelper.getFood_Disease(s);
                res.moveToFirst();
                while (!res.isAfterLast()){
                   int fid= res.getInt(0);
                    for (FoodProduct sFood:arrayList
                            ) {
                        if (sFood.getId() == fid) {
                            sFood.setChecked(true);
                            sFood.setStatus("Disabled");

                        }
                    }
                    res.moveToNext();

            }



            return arrayList;
        }

        Spinner Dis;
        ListView FoodList;
        EditText Dname;
        Button edDis;
        DataBaseHelper db;
        ArrayList<FoodProduct> fl;
        private FoodProducts adapter;
        ArrayAdapter<String> ladapter;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View root= inflater.inflate(R.layout.fragment_edit_disease, container, false);
            Dis=(Spinner)root.findViewById(R.id.EditDiseases);
            edDis=(Button)root.findViewById(R.id.Edit_DiseaseData);
            edDis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db=new DataBaseHelper(getContext());
                    int did=db.getDisId(Dis.getSelectedItem().toString());
                    db.Delete("Food_Disease","FDisease_ID",did+"");
                  //  db.Delete("Delete from Food_Disease where FDisease_ID="+did);

                    for (FoodProduct sele : fl) {
                        if (sele.isChecked()) {
                            DataBaseHelper db = new DataBaseHelper(getContext());
                            int fid=sele.getId();
                             db.insertFood_Disease(fid,did);

                        }
                    }
                    if(Dname.getText().toString()!="") {
                        db.Update("Disease", "Disease_Name", Dname.getText().toString(), "id", did + "");
                    }
                   // db.Update("Update Disease set Disease_Name='"+Dname.getText()+"' where id="+did);

                }
            });
            FoodList=(ListView)root.findViewById(R.id.EditFoodData);
            Dname=(EditText)root.findViewById(R.id.EditDiseaseName);
            db=new DataBaseHelper(getContext());
           ArrayList<String> disea= db.getAllDiseases();
            ladapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, disea);
            Dis.setAdapter(ladapter);
            Dis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String c= adapterView.getItemAtPosition(i).toString();
                    fl=getProductos(c);
                    adapter= new FoodProducts(getContext(),0,fl);
                    FoodList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            FoodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    final  FoodProduct dataModel = (FoodProduct) adapterView.getItemAtPosition(i);

                    if (dataModel.isChecked()) {
                        dataModel.setChecked(false);
                        dataModel.setStatus("Enabled");
                    }
                    else {
                        dataModel.setChecked(true);
                        dataModel.setStatus("Disabled");
                    }
                        adapter.notifyDataSetChanged();


                }
            });

            return root;
        }

    }*/

  /*  public static class AddFoodFragment extends Fragment {
ImageButton imgbtn;
        Button btnAddFood;
        EditText fname,FoodCal,FoodUnit;
        Spinner FoodCat;
        ImageView img;

        ImageView imageView;
        public AddFoodFragment() {
            // Required empty public constructor
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment

           View root=inflater.inflate(R.layout.fragment_add_food, container, false);
            imgbtn=(ImageButton)root.findViewById(R.id.setFoodImg);
            btnAddFood=(Button)root.findViewById(R.id.Add_FoodData);
            fname=(EditText)root.findViewById(R.id.EntryFoodName);
            FoodCal=(EditText)root.findViewById(R.id.EntryFoodCal);
            FoodUnit=(EditText)root.findViewById(R.id.EntryFoodUnit);
            FoodCat=(Spinner)root.findViewById(R.id.FoodCat);
            imageView=(ImageView) root.findViewById(R.id.imgview);
            btnAddFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(fname.getText().toString()=="" ||FoodCal.getText().toString()=="" ||FoodUnit.getText().toString()==""||FoodCat.getSelectedItem().toString()==""||imageUri.toString()==""  )
                        Toast.makeText(getContext(),"Please Fill Details",Toast.LENGTH_LONG).show();
                    else{
                        DataBaseHelper db=new DataBaseHelper(getContext());
                        db.insertFood(fname.getText().toString(),FoodCal.getText().toString(),FoodUnit.getText().toString(),FoodCat.getSelectedItem().toString(),imageUri.toString());
                        Toast.makeText(getContext(),"Saved Successfully",Toast.LENGTH_LONG).show();
                    }
                }
            });

            imgbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),"Chose File from Gallery",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(
                            Intent.ACTION_PICK);//,                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);


                    i.setType("image/*");
                    startActivityForResult(i, RESULT_LOAD_IMAGE);

                }
            });


            return root;
        }
        byte[] byteArray=null;
        Uri imageUri;
        static   int  RESULT_LOAD_IMAGE=1;
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == RESULT_LOAD_IMAGE && null != data) {
                imageUri = data.getData();

                imageView.setImageURI(imageUri);
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor =getActivity().getContentResolver().query(imageUri,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);

              BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bmp = drawable.getBitmap();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byteArray = stream.toByteArray();

            }
        }

    }


*/
}