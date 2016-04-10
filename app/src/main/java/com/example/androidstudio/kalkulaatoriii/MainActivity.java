package com.example.androidstudio.kalkulaatoriii;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

import com.example.androidstudio.kalkulaatoriii.parcelable.DayStatisticParcelable;
import com.example.androidstudio.kalkulaatoriii.parcelable.ExpressionParcelable;
import com.example.androidstudio.kalkulaatoriii.parcelable.ExpressionParcelableComparatorByDate;
import com.example.androidstudio.kalkulaatoriii.parcelable.OperationParcelable;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private List<CalcHistoryItem> calcHistoryItemList = new ArrayList<CalcHistoryItem>();
    private List<DayStatistic> dayStatisticList = new ArrayList<DayStatistic>();
    TableLayout table_layout;
    AutoResizeTextView calcText;
    Button bReset;
    Button bBack;
    Button bDivide;
    Button bMultiple;
    Button bSubtract;
    Button bAdd;
    Button bResult;
    Button bComma;
    Button bZero;
    Button bOne;
    Button bTwo;
    Button bThree;
    Button bFour;
    Button bFive;
    Button bSix;
    Button bSeven;
    Button bEight;
    Button bNine;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calcText = (AutoResizeTextView) findViewById(R.id.calc_text);
        bReset = (Button) findViewById(R.id.button_reset);
        bBack = (Button) findViewById(R.id.button_back);
        bDivide = (Button) findViewById(R.id.button_divide);
        bMultiple = (Button) findViewById(R.id.button_multiple);
        bSubtract = (Button) findViewById(R.id.button_subtract);
        bAdd = (Button) findViewById(R.id.button_add);
        bResult = (Button) findViewById(R.id.button_result);
        bComma = (Button) findViewById(R.id.button_comma);
        bZero = (Button) findViewById(R.id.button_zero);
        bOne = (Button) findViewById(R.id.button_one);
        bTwo = (Button) findViewById(R.id.button_two);
        bThree = (Button) findViewById(R.id.button_three);
        bFour = (Button) findViewById(R.id.button_four);
        bFive = (Button) findViewById(R.id.button_five);
        bSix = (Button) findViewById(R.id.button_six);
        bSeven = (Button) findViewById(R.id.button_seven);
        bEight = (Button) findViewById(R.id.button_eight);
        bNine = (Button) findViewById(R.id.button_nine);
        WizardPagerAdapter pagerAdapter = new WizardPagerAdapter();
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(pagerAdapter);
        table_layout = (TableLayout) findViewById(R.id.tableLayout);
        sendCalculatorBroadcast("");
        sendDatabaseCalculatorBroadcast("GET_ALL_EXPRESSION", null);
        sendDatabaseCalculatorBroadcast("GET_ALL_DAYSTATISTIC", null);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void setStatisticTable(List<DayStatistic> dayStatistics) {
        table_layout.removeAllViews();
        List<String> differentOperand = new ArrayList<String>();
        for (DayStatistic dayStatistic : dayStatistics) {
            for (DayStatistic.Operand operand : dayStatistic.getOperands()) {
                if (differentOperand.indexOf(operand.getOp()) == -1) {
                    differentOperand.add(operand.getOp());
                }
            }
        }
        int rows = dayStatistics.size() + 1;
        int[] totalCount = new int[differentOperand.size()];
        int cols = differentOperand.size() + 1;
        for (int r = 1; r <= rows; r++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            for (int c = 1; c <= cols; c++) {
                TextView tv = new TextView(this);
                tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT));
                tv.setPadding(5, 5, 5, 5);
                if (r == 1) {
                    if (c == 1) {
                        tv.setText("Date");
                    } else {
                        tv.setText(differentOperand.get(c - 2));
                    }
                    tv.setBackgroundResource(R.drawable.cell_shape_header);
                    tv.setTextColor(getResources().getColor(R.color.white));
                } else {
                    if (c == 1) {
                        tv.setText(dayStatistics.get(r - 2).getDate());
                    } else {
                        String s = differentOperand.get(c - 2);
                        int count = dayStatistics.get(r - 2).getCount(s);
                        totalCount[c - 2] += count;
                        tv.setText(String.valueOf(count));
                        //tv.setText(dayStatistics.get(r - 2).getCount(differentOperand.get(c - 2)));
                    }
                    if ((r + 1) % 2 == 0) {
                        tv.setBackgroundResource(R.drawable.cell_shape);
                    }
                }
                row.addView(tv);
            }
            table_layout.addView(row);
        }
        TableRow row = new TableRow(this);
        row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        for (int c = 1; c <= cols; c++) {
            TextView tv = new TextView(this);
            tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            tv.setPadding(5, 5, 5, 5);
            if (c == 1) {
                tv.setText("Total:");
            } else {
                tv.setText(String.valueOf(totalCount[c - 2]));
            }
            tv.setBackgroundResource(R.drawable.cell_shape_footer);
            tv.setTextColor(getResources().getColor(R.color.white));
            tv.setTextAppearance(this, R.style.text_bold);
            row.addView(tv);
        }
        table_layout.addView(row);
    }


    private void refresh() {

    }

    private void populateListView() {
        ArrayAdapter<CalcHistoryItem> adapter = new CalcHistoryList(this, calcHistoryItemList);
        ListView list = (ListView) findViewById(R.id.listView_calc);
        //list.setAdapter(adapter);
        //ArrayAdapter<ExpressionItem> adapter = new ExpressionArrayAdapter(this,calcHistoryItemList.get(1).expressionItems);
        //ListView list = (ListView)convertView.findViewById(R.id.calc_history_item_listview_expression);
        list.setAdapter(adapter);
        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            int mScrollState = AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                mScrollState = scrollState;
            }

            @Override
            public void onScroll(AbsListView list, int firstItem,
                                 int visibleCount, int totalCount) {
                if (mScrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    return;
                }
                for (int i = 0; i < visibleCount; i++) {
                    View listItem = list.getChildAt(i);
                    if (listItem == null) {
                        break;
                    }
                    TextView title = (TextView) listItem
                            .findViewById(R.id.title);
                    int topMargin = 0;
                    if (i == 0) {
                        int top = listItem.getTop();
                        int height = listItem.getHeight();
                        if (top < 0) {
                            if (title.getHeight() < (top + height)) {
                                topMargin = -top;
                            } else {
                                topMargin = height - title.getHeight();
                            }
                        }
                    }
                    ((ViewGroup.MarginLayoutParams) title.getLayoutParams()).topMargin = topMargin;
                    listItem.requestLayout();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void calcButtonClicked(View view) {
        Button b = (Button) view;
        sendCalculatorBroadcast(b.getText().toString());
    }

    private void sendCalculatorBroadcast(String command) {
        Intent intent = new Intent();
        intent.setAction("com.ee.calculatorBroadcastRequest");
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        if (command != null) {
            intent.putExtra("BUTTON_COMMAND", command);
        }
        sendOrderedBroadcast(intent, null, new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = getResultExtras(true);
                if (bundle != null) {
                    calcText.setText(bundle.getString("CALC_TEXT"));
                    bReset.setEnabled(bundle.getBoolean("BUTTON_RESET_ENABLED"));
                    bBack.setEnabled(bundle.getBoolean("BUTTON_BACK_ENABLED"));
                    bDivide.setEnabled(bundle.getBoolean("BUTTON_DIVIDE_ENABLED"));
                    bMultiple.setEnabled(bundle.getBoolean("BUTTON_MULTIPLE_ENABLED"));
                    bSubtract.setEnabled(bundle.getBoolean("BUTTON_SUBTRACT_ENABLED"));
                    bAdd.setEnabled(bundle.getBoolean("BUTTON_ADD_ENABLED"));
                    bResult.setEnabled(bundle.getBoolean("BUTTON_RESULT_ENABLED"));
                    bComma.setEnabled(bundle.getBoolean("BUTTON_COMMA_ENABLED"));
                    bZero.setEnabled(bundle.getBoolean("BUTTON_ZERO_ENABLED"));
                    bOne.setEnabled(bundle.getBoolean("BUTTON_ONE_ENABLED"));
                    bTwo.setEnabled(bundle.getBoolean("BUTTON_TWO_ENABLED"));
                    bThree.setEnabled(bundle.getBoolean("BUTTON_THREE_ENABLED"));
                    bFour.setEnabled(bundle.getBoolean("BUTTON_FOUR_ENABLED"));
                    bFive.setEnabled(bundle.getBoolean("BUTTON_FIVE_ENABLED"));
                    bSix.setEnabled(bundle.getBoolean("BUTTON_SIX_ENABLED"));
                    bSeven.setEnabled(bundle.getBoolean("BUTTON_SEVEN_ENABLED"));
                    bEight.setEnabled(bundle.getBoolean("BUTTON_EIGHT_ENABLED"));
                    bNine.setEnabled(bundle.getBoolean("BUTTON_NINE_ENABLED"));
                    byte[] expressionParcelableData = bundle.getByteArray("EXPRESSION_DATA");
                    if (expressionParcelableData != null) {
                        Parcel parcel = Parcel.obtain();
                        parcel.unmarshall(expressionParcelableData, 0, expressionParcelableData.length);
                        parcel.setDataPosition(0);
                        ExpressionParcelable expressionParcelable = new ExpressionParcelable(parcel);
                        if (expressionParcelable != null) {
                            sendDatabaseCalculatorBroadcast("PUT_EXPRESSION", expressionParcelable);
                        }
                    }
                }
            }
        }, null, Activity.RESULT_OK, null, null);
    }

    private void sendDatabaseCalculatorBroadcast(String command, Object obj) {
        Intent intent = new Intent();
        intent.setAction("com.ee.databaseCalculatorBroadcastRequest");
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.putExtra("COMMAND", command);
        if (command.equals("PUT_EXPRESSION")) {
            ExpressionParcelable expressionParcelable = (ExpressionParcelable) obj;
            Parcel parcel = Parcel.obtain();
            expressionParcelable.writeToParcel(parcel, 0);
            intent.putExtra("DATA", parcel.marshall());
        }
        sendOrderedBroadcast(intent, null, new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = getResultExtras(true);
                if (bundle != null) {
                    bundle.setClassLoader(ExpressionParcelable.class.getClassLoader());
                    bundle.setClassLoader(OperationParcelable.class.getClassLoader());
                    bundle.setClassLoader(DayStatisticParcelable.class.getClassLoader());
                    String resultType = bundle.getString("RESULT_TYPE");
                    if (resultType != null) {
                        if (resultType.equals("OK")) {
                            sendDatabaseCalculatorBroadcast("GET_ALL_EXPRESSION", null);
                            sendDatabaseCalculatorBroadcast("GET_ALL_DAYSTATISTIC", null);
                        } else if (resultType.equals("ERROR")) {

                        } else {
                            if (resultType.equals("ALL_DAYSTATISTIC_DATA")) {
                                int arrayCount = bundle.getInt("DATA_ARRAY_COUNT");
                                List<DayStatisticParcelable> dayStatisticParcelables = new ArrayList<DayStatisticParcelable>();
                                for (int i = 0; i < arrayCount; i++) {
                                    Parcel parcel = Parcel.obtain();
                                    byte[] data = bundle.getByteArray("DATA_" + i);
                                    parcel.unmarshall(data, 0, data.length);
                                    parcel.setDataPosition(0);
                                    DayStatisticParcelable dayStatisticParcelable = new DayStatisticParcelable(parcel);
                                    if (dayStatisticParcelable != null) {
                                        dayStatisticParcelables.add(dayStatisticParcelable);
                                    }
                                }
                                setDayStatisticList(dayStatisticParcelables);
                                setStatisticTable(dayStatisticList);

                            } else if (resultType.equals("ALL_EXPRESSION_DATA")) {
                                int arrayCount = bundle.getInt("DATA_ARRAY_COUNT");
                                List<ExpressionParcelable> expressionParcelables = new ArrayList<ExpressionParcelable>();
                                for (int i = 0; i < arrayCount; i++) {
                                    Parcel parcel = Parcel.obtain();
                                    byte[] data = bundle.getByteArray("DATA_" + i);
                                    parcel.unmarshall(data, 0, data.length);
                                    parcel.setDataPosition(0);
                                    ExpressionParcelable expressionParcelable = new ExpressionParcelable(parcel);
                                    if (expressionParcelable != null) {
                                        expressionParcelables.add(expressionParcelable);
                                    }
                                }
                                if (expressionParcelables != null) {
                                    Collections.sort(expressionParcelables, new ExpressionParcelableComparatorByDate());
                                    setCalcHistoryItemList(expressionParcelables);
                                    populateListView();
                                }
                            }
                        }
                    }
                }
            }
        }, null, Activity.RESULT_OK, null, null);
    }

    private void setDayStatisticList(List<DayStatisticParcelable> dayStatisticParcelables) {
        dayStatisticList = new ArrayList<DayStatistic>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        while (dayStatisticParcelables.size() != 0) {
            String date = dateFormat.format(dayStatisticParcelables.get(0).created);
            DayStatistic dayStatistic = new DayStatistic(date);
            List<DayStatistic.Operand> operands = new ArrayList<DayStatistic.Operand>();
            List<DayStatisticParcelable> removeStatisticParcelable = new ArrayList<DayStatisticParcelable>();
            for (DayStatisticParcelable dayStatisticParcelable : dayStatisticParcelables) {
                if (date.equals(dateFormat.format(dayStatisticParcelable.created))) {
                    removeStatisticParcelable.add(dayStatisticParcelable);
                    DayStatistic.Operand operand = new DayStatistic.Operand(dayStatisticParcelable.operand, dayStatisticParcelable.count);
                    operands.add(operand);
                }
            }
            dayStatistic.setOperands(operands);
            dayStatisticParcelables.removeAll(removeStatisticParcelable);
            dayStatisticList.add(dayStatistic);
        }
    }

    private void setCalcHistoryItemList(List<ExpressionParcelable> expressionParcelables) {
        calcHistoryItemList.clear();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        while (expressionParcelables.size() != 0) {
            String date = dateFormat.format(expressionParcelables.get(0).created);
            CalcHistoryItem calcHistoryItem = new CalcHistoryItem(date);
            List<ExpressionItem> expressionItems = new ArrayList<ExpressionItem>();
            List<ExpressionParcelable> removeExpressionParcelable = new ArrayList<ExpressionParcelable>();
            for (ExpressionParcelable expressionParcelable : expressionParcelables) {
                if (date.equals(dateFormat.format(expressionParcelable.created))) {
                    removeExpressionParcelable.add(expressionParcelable);
                    expressionItems.add(getExpressionItemFromExpressionParcelable(expressionParcelable));
                }
            }
            expressionParcelables.removeAll(removeExpressionParcelable);
            calcHistoryItem.setExpressionItems(expressionItems);
            calcHistoryItemList.add(calcHistoryItem);
        }
    }

    ExpressionItem getExpressionItemFromExpressionParcelable(ExpressionParcelable expressionParcelable) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        ExpressionItem expressionItem = new ExpressionItem();
        expressionItem.setTime(timeFormat.format(expressionParcelable.created));
        expressionItem.setData(expressionParcelable.data + "=" + expressionParcelable.result);
        List<OperationItem> operationItems = new ArrayList<OperationItem>();
        for (OperationParcelable operationParcelable : expressionParcelable.operationParcelables) {
            operationItems.add(getOperationItemFromOperationParcelable(operationParcelable));
        }
        expressionItem.setOperationItems(operationItems);
        return expressionItem;
    }

    private OperationItem getOperationItemFromOperationParcelable(OperationParcelable operationParcelable) {
        OperationItem operationItem = new OperationItem();
        operationItem.setNum1(getStringFromFloat(operationParcelable.num1));
        operationItem.setNum2(getStringFromFloat(operationParcelable.num2));
        operationItem.setOp(operationParcelable.operand);
        operationItem.setResult(getStringFromFloat(operationParcelable.result));
        return operationItem;
    }

    String getStringFromFloat(float f) {
        if (Float.isInfinite(f)) {
            if (Float.POSITIVE_INFINITY == f) {
                return "\u221E";
            } else {
                return "-\u221E";
            }
        } else if (Float.isNaN(f)) {
            return "NaN";
        } else {
            if (f == (long) f) {
                return String.format("%d", (long) f);
            } else {
                return String.format("%s", f);
            }
        }
    }

    private void setCalcHistoryItem(ExpressionParcelable expressionParcelable) {

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.androidstudio.kalkulaatoriii/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.androidstudio.kalkulaatoriii/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
