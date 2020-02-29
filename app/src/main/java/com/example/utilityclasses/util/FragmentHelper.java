package com.example.utilityclasses.util;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

/** The purpose of this class is to make fragment transaction easy and hassle free
 * All methods are static and easy to use
 * Just pass the Activity activity, the container Id in which you want to replace or add or remove a
 * fragment from and the Fragment Object.
 */
public class FragmentHelper {

    public static void replaceFragment(AppCompatActivity activity, int containerId, Fragment fragment) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(containerId, fragment).commit();
    }

    public static void addFragment(AppCompatActivity activity, int containerId, Fragment fragment) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        fragmentManager.beginTransaction().add(containerId, fragment).commit();
    }

    public static void replaceFragmentAndAddToStack(AppCompatActivity activity, int containerId, Fragment fragment, String fragmentName) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(containerId, fragment).addToBackStack(fragmentName).commit();
    }

    public static void addFragmentAndAddToStack(AppCompatActivity activity, int containerId, Fragment fragment, String fragmentName) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        fragmentManager.beginTransaction().add(containerId, fragment).addToBackStack(fragmentName).commit();
    }

    public static void removeLastFragment(AppCompatActivity activity, Fragment fragment) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        fragmentManager.beginTransaction().remove(fragment).commit();
    }

    public static int getLastFragment(AppCompatActivity activity){
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        List<Fragment> lastFragment = fragmentManager.getFragments();
        Fragment fragment = lastFragment.get(lastFragment.size() - 1);
        return fragment.getId();
    }

}
