package com.example.sanyolewis.fragmentexample;

        import android.app.Fragment;
        import android.app.FragmentManager;
        import android.app.FragmentTransaction;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn1,btn2,btn3;
    private Fragment fragment2,fragment3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = this.findViewById(R.id.add);
        btn2 = this.findViewById(R.id.remove);
        btn3 = this.findViewById(R.id.replace);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);

        fragment2 = new SecondFragment();
        fragment3 = new ThreeFragment();

    }

    @Override
    public void onClick(View v) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (v.getId()){
            case R.id.add:
            {
                if (!fragment2.isAdded()){
                    transaction.add(R.id.frame2,fragment2);
                }
                if (!fragment3.isAdded()){
                    transaction.add(R.id.frame3,fragment3);
                }
            }
            break;
            case R.id.replace:
            {
                if (fragment3.isAdded()){
                    transaction.replace(R.id.frame3,fragment2);
                }
            }
            break;
            case R.id.remove:
            {
                if (fragment3.isAdded()){
                    transaction.remove(fragment2);
                }
            }
            break;
        }
        transaction.commit();
    }
}
