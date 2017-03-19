# ViewVisibilityCheck

It checks if a View is currently visible on screen, it could be inside a ScrollView or a ViewPager, and it runs a Runnable.

You can load content or display a message only when a specific View is displayed.

### Example

```java
final View v12 = rootView.findViewById(R.id.id12);
check12 = ViewVisibilityCheck.newViewVisibilityCheck(v12, new Runnable() {
    @Override
    public void run() {
        Toast.makeText(getContext(), "View 12", Toast.LENGTH_LONG).show();
    }
});
```

