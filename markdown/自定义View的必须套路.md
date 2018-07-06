
##自定义View的必须套路

###1.自定义View首先要确定这个View需要在xml里接受哪些属性？

  在IndexBar里，我们先需要两个属性，每个索引的文字大小和手指按下时整个View的背景，
  
  即在attrs.xml如下定义：

	<attr name="textSize" format="dimension" />
    <declare-styleable name="IndexBar">
        <attr name="textSize" />
        <attr name="pressBackground" format="color" />
    </declare-styleable>

###2.在View的构造方法中获得我们自定义的属性

  套路代码如下，都是套路，记得使用完最后要将typeArray对象 recycle()。

	int textSize = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics());//默认的TextSize
        mPressedBackground = Color.BLACK;//默认按下是纯黑色
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.IndexBar, defStyleAttr, 0);
        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.IndexBar_textSize:
                    textSize = typedArray.getDimensionPixelSize(attr, textSize);
                    break;
                case R.styleable.IndexBar_pressBackground:
                    mPressedBackground = typedArray.getColor(attr, mPressedBackground);
                default:
                    break;
            }
        }
        typedArray.recycle();

###3. 重写onMesure()方法（可选）

onMeasure()方法里，主要就是遍历一遍indexDatas，得到index最大宽度和高度。然后根据三种测量模式，分配不同的值给View，
EXACLTY就分配具体的测量值（match_parent,确定数值），
AT_MOST就分配父控件能给的最大值和自己需要的值之间的最小值。（保证不超过父控件限定的值）
UNSPECIFIED则分配自己需要的值。（随心所欲）

	@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //取出宽高的MeasureSpec  Mode 和Size
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        int measureWidth = 0, measureHeight = 0;//最终测量出来的宽高

        //得到合适宽度：
        Rect indexBounds = new Rect();//存放每个绘制的index的Rect区域
        String index;//每个要绘制的index内容
        for (int i = 0; i < mIndexDatas.size(); i++) {
            index = mIndexDatas.get(i);
            mPaint.getTextBounds(index, 0, index.length(), indexBounds);//测量计算文字所在矩形，可以得到宽高
            measureWidth = Math.max(indexBounds.width(), measureWidth);//循环结束后，得到index的最大宽度
            measureHeight = Math.max(indexBounds.width(), measureHeight);//循环结束后，得到index的最大高度，然后*size
        }
        measureHeight *= mIndexDatas.size();
        switch (wMode) {
            case MeasureSpec.EXACTLY:
                measureWidth = wSize;
                break;
            case MeasureSpec.AT_MOST:
                measureWidth = Math.min(measureWidth, wSize);//wSize此时是父控件能给子View分配的最大空间
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }

        //得到合适的高度：
        switch (hMode) {
            case MeasureSpec.EXACTLY:
                measureHeight = hSize;
                break;
            case MeasureSpec.AT_MOST:
                measureHeight = Math.min(measureHeight, hSize);//wSize此时是父控件能给子View分配的最大空间
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }

        setMeasuredDimension(measureWidth, measureHeight);
    }

###4.重写onDraw()方法

整理一下需求和思路：
利用index数据源的size，和控件可绘制的高度(高度-paddingTop-paddingBottom)，求出每个index区域的高度mGapHeight。
每个index在绘制时，都是处于水平居中，竖直方向上在mGapHeight区域高度内居中。
思路整理清楚，代码很简单如下：

	public static String[] INDEX_STRING = {"A", "B", "C", "D", "E", "F", "G", "H", "I","J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V","W", "X", "Y", "Z", "#"};//#在最后面（默认的数据源）
    private List<String> mIndexDatas;//索引数据源
    private int mGapHeight;//每个index区域的高度
    .....
    mIndexDatas = Arrays.asList(INDEX_STRING);//数据源

在onSizeChanged方法里，获取控件的宽高，并计算出mGapHeight:

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mGapHeight = (mHeight - getPaddingTop() - getPaddingBottom()) / mIndexDatas.size();
    }

最后在onDraw()方法里绘制,
如果对于竖直居中baseLine的计算不太理解可以先放置，这块的确挺绕人，后面应该会写一篇 canvas.drawText()x y坐标计算的小短文.
可记住重点就是 Paint默认的TextAlign是Left，即x方向，左对齐，所以x坐标决定绘制文字的左边界。
y坐标是绘制文字的baseLine位置。

    @Override
    protected void onDraw(Canvas canvas) {
        int t = getPaddingTop();//top的基准点(支持padding)
        Rect indexBounds = new Rect();//存放每个绘制的index的Rect区域
        String index;//每个要绘制的index内容
        for (int i = 0; i < mIndexDatas.size(); i++) {
            index = mIndexDatas.get(i);
            mPaint.getTextBounds(index, 0, index.length(), indexBounds);//测量计算文字所在矩形，可以得到宽高
            Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();//获得画笔的FontMetrics，用来计算baseLine。因为drawText的y坐标，代表的是绘制的文字的baseLine的位置
            int baseline = (int) ((mGapHeight - fontMetrics.bottom - fontMetrics.top) / 2);//计算出在每格index区域，竖直居中的baseLine值
            canvas.drawText(index, mWidth / 2 - indexBounds.width() / 2, t + mGapHeight * i + baseline, mPaint);//调用drawText，居中显示绘制index
        }
    }

以上四步基本完成了IndexBar的绘制工作，下面我们为它添加一些行为的响应。
