package com.cauly.nativead;

/* Athrun - Android automation testing Framework.
Copyright (C) 2010-2012 TaoBao UI AutoMan Team

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., HuaXing road, Hangzhou,China. 
Email:taichan@taobao.com,shidun@taobao.com,bingyang.djj@taobao.com
*/

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
* This class contains view methods. Example is getViews(),
* 
* @author bingyang.djj
*/
public final class ViewUtils {

	private ViewUtils() {
		throw new AssertionError();
	}

	/**
	 * Returns views from the shown DecorViews.
	 * 
	 * @param onlySufficientlyVisible
	 *            if only sufficiently visible views should be returned
	 * @return all the views contained in the DecorViews
	 * 
	 */
	public static ArrayList<View> getAllViews(boolean onlySufficientlyVisible) {
		final View[] views = getDecorViews();
		final ArrayList<View> allViews = new ArrayList<View>();
		final View[] popupViews = getPopupViews(views);

		if (views != null && views.length > 0) {
			View view;

			for (int i = 0; i < popupViews.length; i++) {
				view = popupViews[i];

				try {
					addChildren(allViews, (ViewGroup) view,
							onlySufficientlyVisible);

				} catch (Exception ignored) {
					// do nothing here
				}
			}

			view = getNormalViews(views);
			try {
				addChildren(allViews, (ViewGroup) view, onlySufficientlyVisible);

			} catch (Exception ignored) {
				// do nothing here
			}
		}

		Log.i("Changju","There are " + allViews.size()
				+ " views in current screen.");
		return allViews;
	}

	private static final View getNormalViews(View[] views) {
		final View[] decorViews = new View[views.length];
		int i = 0;
		View view;

		for (int j = 0; j < views.length; j++) {
			view = views[j];
			if (view.getClass()
					.getName()
					.equals("com.android.internal.policy.impl.PhoneWindow$DecorView")) {
				decorViews[i] = view;
				i++;
			}
		}
		return getContainerViews(decorViews);
	}

	private static final View getContainerViews(View[] views) {
		View container = null;
		long drawingTime = 0;
		View view;

		for (int i = 0; i < views.length; i++) {
			view = views[i];
			if (view != null && view.isShown() && view.hasWindowFocus()
					&& view.getDrawingTime() > drawingTime) {
				container = view;
				drawingTime = view.getDrawingTime();
			}
		}
		return container;
	}

	private static final View[] getPopupViews(View[] views) {
		final View[] decorViews = new View[views.length];
		int i = 0;
		View view;

		for (int j = 0; j < views.length; j++) {
			view = views[j];
			if (!(view.getClass().getName()
					.equals("com.android.internal.policy.impl.PhoneWindow$DecorView"))) {
				decorViews[i] = view;
				i++;
			}
		}
		return decorViews;
	}

	/**
	 * Adds all children of {@code viewGroup} (recursively) into {@code views}.
	 * 
	 * @param views
	 *            an {@code ArrayList} of {@code View}s
	 * @param viewGroup
	 *            the {@code ViewGroup} to extract children from
	 * @param onlySufficientlyVisible
	 *            if only sufficiently visible views should be returned
	 * 
	 */
	static void addChildren(ArrayList<View> views, ViewGroup viewGroup,
			boolean onlySufficientlyVisible) {
		for (int i = 0; i < viewGroup.getChildCount(); i++) {
			final View child = viewGroup.getChildAt(i);

			if (onlySufficientlyVisible && child.isShown()) {
				views.add(child);

			} else if (!onlySufficientlyVisible) {
				views.add(child);
			}

			if (child instanceof ViewGroup) {
				addChildren(views, (ViewGroup) child, onlySufficientlyVisible);
			}
		}
	}
	
	private static Class<?> windowManager;
	static {
		try {
			String windowManagerClassName;
			if (android.os.Build.VERSION.SDK_INT >= 17) {
				windowManagerClassName = "android.view.WindowManagerGlobal";
			} else {
				windowManagerClassName = "android.view.WindowManagerImpl";
			}
			windowManager = Class.forName(windowManagerClassName);

		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	private static String windowManagerString;
	private static String getWindowManagerString() {
		if (windowManagerString == null) {
			if (android.os.Build.VERSION.SDK_INT >= 17) {
				windowManagerString = "sDefaultWindowManager";
			} else if (android.os.Build.VERSION.SDK_INT >= 13) {
				windowManagerString = "sWindowManager";
			} else {
				windowManagerString = "mWindowManager";
			}
		}
		return windowManagerString;
	}


	public static View[] getDecorViews() {
		Object wmi;
		try {
			Field views = windowManager.getDeclaredField("mViews");
			views.setAccessible(true);
			Field instanceField = windowManager
					.getDeclaredField(getWindowManagerString());
			instanceField.setAccessible(true);
			wmi = instanceField.get(null);
			synchronized (wmi) {
				return ((View[]) views.get(wmi)).clone();
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			// } catch (InvocationTargetException e) {
			// e.printStackTrace();
			// } catch (NoSuchMethodException e) {
			// e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}

		throw new RuntimeException("getWindowDecorViews() return null.");
	}

	/**
	 * Removes invisible {@code View}s
	 * 
	 * @param viewList
	 *            an {@code ArrayList} with {@code View}s that is being checked
	 *            for invisible {@code View}s.
	 * @return a filtered {@code ArrayList} with no invisible {@code View}s.
	 */
	public static <T extends View> ArrayList<T> removeInvisibleViews(
			ArrayList<T> viewList) {
		ArrayList<T> tmpViewList = new ArrayList<T>(viewList.size());
		for (T view : viewList) {
			if (view != null && view.getVisibility() != View.GONE
					&& view.getVisibility() != View.INVISIBLE) {
				tmpViewList.add(view);
			}
		}
		return tmpViewList;
	}

	public static <T extends View> ArrayList<T> removeUnshownViews(
			ArrayList<T> viewList) {
		ArrayList<T> tmpViewList = new ArrayList<T>(viewList.size());
		for (T view : viewList) {
			if (view != null && view.isShown() && view.hasWindowFocus()) {
				tmpViewList.add(view);
			}
		}
		return tmpViewList;
	}

	/**
	 * Filters views
	 * 
	 * @param classToFilterBy
	 *            the class to filter
	 * @param viewList
	 *            the ArrayList to filter form
	 * @return an ArrayList with filtered views
	 */
	@SuppressWarnings("unchecked")
	public static <T extends View> ArrayList<T> filterViews(
			Class<?> classToFilterBy, ArrayList<View> viewList) {
		ArrayList<T> filteredViews = new ArrayList<T>(viewList.size());
		for (View view : viewList) {
			if (view != null
					&& classToFilterBy.isAssignableFrom(view.getClass())) {
				filteredViews.add((T) view);
			}
		}
		return filteredViews;
	}


	public static boolean viewIsSameOrDescendant(View child, View parent) {

		if (child == parent) {
			return true;
		}

		ViewParent middleParent = getViewParent(child);

		while ((middleParent != null) && (middleParent != parent)) {
			middleParent = getViewParent(middleParent);
		}

		return middleParent == parent;
	}

	private static ViewParent getViewParent(View view) {
		return view.getParent();
	}

	private static ViewParent getViewParent(ViewParent view) {
		return view.getParent();
	}
}