package es.uniovi.asw.menus;

import es.uniovi.asw.model.User;

public interface Menu {

	public Menu chooseOption(int option, User currentUser);
	public void showOptions();
}
