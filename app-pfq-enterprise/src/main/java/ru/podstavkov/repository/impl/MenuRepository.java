package ru.podstavkov.repository.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import ru.podstavkov.model.MenuItem;
import ru.podstavkov.utils.Messages;


@Repository
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class MenuRepository {

	private Map<String, LinkedHashMap<String, MenuItem>> menu = new LinkedHashMap<String, LinkedHashMap<String, MenuItem>>();
	

    @Autowired
    private Messages message;

	@PostConstruct
	private void init() {
		merge("admsidebar");
		merge("admtopbar");
	
		merge("admsidebar",new MenuItem("adm.link.s.dashboard",	"/secure", 				false, "ti-panel"));
		merge("admsidebar",new MenuItem("adm.link.s.article", 	"/secure/articles", 	false, "ti-book"));
		merge("admsidebar",new MenuItem("adm.link.s.category", 	"/secure/categories", 	false, "ti-layers-alt"));
		merge("admsidebar",new MenuItem("adm.link.s.teg", 		"/secure/tegs", 		false, "ti-location-arrow"));
		merge("admsidebar",new MenuItem("adm.link.s.menu", 		"/secure/menus", 		false, "ti-menu-alt"));
		merge("admsidebar",new MenuItem("adm.link.s.user", 		"/secure/users", 		false, "ti-user"));
		merge("admsidebar",new MenuItem("adm.link.s.role", 		"/secure/roles", 		false, "ti-star"));
		
		MenuItem language = new MenuItem("lang.change", 		"#", 					false, "ti-flag-alt-2");
		List<MenuItem> children = new ArrayList<>();
		children.add(new MenuItem("lang.ru", 		"/international?lang=ru", 			false, ""));
		children.add(new MenuItem("lang.en", 		"/international?lang=en", 			false, ""));
		language.setChildren(children);
		
		merge("admtopbar",new MenuItem("adm.link.profile", 		"/secure/profile", 		false, "ti-star"));
		merge("admtopbar",language);
		merge("admtopbar",new MenuItem("header.link.logout", 	"/logout", 				false, "ti-ruler"));

		
		 
	}

	public void merge(String nameMenu) {
		if (nameMenu == null || nameMenu.isEmpty())
			return;
		LinkedHashMap<String, MenuItem> innerMenu = new LinkedHashMap<String, MenuItem>(); 
		menu.put(nameMenu, innerMenu);
	}
	

	public void remove(String nameMenu) {
		if (nameMenu == null || nameMenu.isEmpty())
			return;
		menu.remove(nameMenu);
	}
	
	public LinkedHashMap<String, MenuItem>  findOne(String nameMenu) {
		if (nameMenu == null || nameMenu.isEmpty())
			return null;
		return menu.get(nameMenu);
	}
	
	public void merge(String nameMenu, MenuItem menuItem) {
		if (menuItem == null || nameMenu == null ||  nameMenu.isEmpty())
			return;
		
		LinkedHashMap<String, MenuItem> innerMenu = findOne(nameMenu);
		innerMenu.put(menuItem.getUri(), menuItem);
	}

	public Collection<MenuItem> getListItems(String nameMenu) {
		return getListItems(nameMenu,"");
	}
	
	public Collection<MenuItem> getListItems(String nameMenu,String menuUrlToActive) {
		if ( nameMenu == null ||  nameMenu.isEmpty())
			return null;
		
		LinkedHashMap<String, MenuItem> innerMenu = new LinkedHashMap<String, MenuItem>();
		
		findOne(nameMenu).forEach((k, v) -> { 
			    try {
					MenuItem clone = v.clone();
					
			        if (clone.getUri().equals(menuUrlToActive)) {
			        	clone.setActive(true);
			        }
			        clone.setText(message.get(v.getText()));
			        innerMenu.put(k, clone);
			        
				} catch (CloneNotSupportedException e) { 
					e.printStackTrace();
				}
			    
		    });

		return innerMenu.values();
	}
	
	public  Map<String, Collection<MenuItem>> getAllMenus(String nameMenu,String menuUrlToActive){ 
		
		 Map<String, Collection<MenuItem>> copyallmenus = new LinkedHashMap<>();
		 
		 menu.forEach((k, v) -> { 
			 List<MenuItem> tmpmenu ;
			
			if (k.equals(nameMenu)) {
				
				tmpmenu = new ArrayList(getListItems(nameMenu, menuUrlToActive));
			}
			else {
				tmpmenu = new ArrayList<>();
				
				v.forEach((ck, cv)-> {
                    
					try {
						MenuItem clone = cv.clone();
				        clone.setText(message.get(cv.getText()));
				        tmpmenu.add(clone);
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}

				});
			}
			
			copyallmenus.put(k, tmpmenu);
			
		 });
		 
		 return copyallmenus;
	}

	public MenuItem findOne(String nameMenu,String menuUrl) {
		if (menuUrl == null || nameMenu == null || menuUrl.isEmpty() || nameMenu.isEmpty())
			return null;
		LinkedHashMap<String, MenuItem> innerMenu = findOne(nameMenu);
		return innerMenu.get(menuUrl);
	}

	public void removeMenuByUri(String nameMenu,String menuUrl) {
		if (menuUrl == null || nameMenu == null || menuUrl.isEmpty() || nameMenu.isEmpty())
			return;
		LinkedHashMap<String, MenuItem> innerMenu = findOne(nameMenu);
		innerMenu.remove(menuUrl);
	}

}
