package es.uniovi.asw.menus;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import es.uniovi.asw.model.User;

public class AbstractMenu implements Menu{

	protected List<String> menuOptions = new ArrayList<String>();
	protected static BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

	
	@Override
	public void showOptions() {
		for (int option = 0; option < menuOptions.size(); option++)
			System.out.println(option+1 + ". " + menuOptions.get(option));
	}

	@Override
	public Menu chooseOption(int option, User currentUser) {
		return null;
	}
}
