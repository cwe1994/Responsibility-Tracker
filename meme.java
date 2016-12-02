package menu;
import android.os.Bundle;
import android.content.Intent;

public class meme //this would be MainActivity extends Activity; MainActivity is basically the main of an android app
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(SavedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		//inflates the menu; adds items to the action bar if present
		getMenuInflater().inflate(R.menu.activity_main, menu); //activity main is an xml file
		return true;
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		super.onOptionsItemSelected(item);
		String message = "Title: " + item.getTitle() + ", id =" + item.getItem();
		
		switch(item.getItemId())
		{
			case R.id.list_devices: //list_devices is the name of the button in the main xml file
				startActivity(new Intent(this, ListDevices.class)); //ListDevices.class would be wherever the list function is
				break;
			//if we need to add any other buttons you would just add more cases
		}
		
		return true;
	}
}
