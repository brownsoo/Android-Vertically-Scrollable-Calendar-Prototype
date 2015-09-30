# Android Vertically Scrollable Calendar Prototype

This is the Android sample project making the Vertically scrollable calendar for study.

### Check point 1
I created the base date to sync page position with particular date.

```java
/** Default year to calculate the page position */
final static int BASE_YEAR = 2015;
/** Default month to calculate the page position */
final static int BASE_MONTH = Calendar.JANUARY;
/** Calendar instance based on default year and month */
final Calendar BASE_CAL;
...
Calendar base = Calendar.getInstance();
base.set(BASE_YEAR, BASE_MONTH, 1);
BASE_CAL = base;
...
```

### Check point 2
Then, we can get the particular date with page position.

```java
public YearMonth getYearMonth(int position) {
  Calendar cal = (Calendar)BASE_CAL.clone();
  cal.add(Calendar.MONTH, position - BASE_POSITION);
  return new YearMonth(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));
}
```





In this project, I use [`VerticalViewPager-1`](VerticalViewPager-1) for just vertical view pager. And I refer to ['SimpleInfiniteCarousel'](SimpleInfiniteCarousel) to make a simple infinite carousel with ViewPager on Android.

I am not good at english. please let me know the misspelled comments in my code.

## 세로 스크롤 달력 프로토타입 안드로이드 예제
이 저장소는 안드로이드에서 세로 스크롤이 가능한 달력을 만들기 위해 만든 목업샘플입니다.
관련 내용을 제 블로그에서 더 확인하실 수 있습니다. [한수댁 작업실](http://blog.hansune.com/595)




