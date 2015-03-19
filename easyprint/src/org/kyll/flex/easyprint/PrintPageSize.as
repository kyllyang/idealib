package org.kyll.flex.easyprint {
import mx.core.FlexGlobals;

/**
 * 纸张规格， 用来定义打印区域尺寸
 */
public class PrintPageSize {
	private static const DPI:int = FlexGlobals.topLevelApplication.runtimeDPI;// 取得当前应用程序运行时 DPI （每英寸像素数量）
	private static const INCH:Number = 2.54;//  1 英寸等于 2.54 厘米
	private static const PIXEL_CM:Number = DPI / INCH;// 每厘米像素数量

	private static const A4_WIDTH:int = 21.0 * PIXEL_CM;// A4 规格
	private static const A4_HEIGHT:int = 29.7 * PIXEL_CM;
	private static const B5_WIDTH:int = 17.6 * PIXEL_CM;// B5 规格
	private static const B5_HEIGHT:int = 25.0 * PIXEL_CM;

	public static const A4_SIZE:PrintPageSize = new PrintPageSize("A4", A4_WIDTH, A4_HEIGHT);
	public static const B5_SIZE:PrintPageSize = new PrintPageSize("B5", B5_WIDTH, B5_HEIGHT);

	public static const LANDSCAPE:String = "横向";
	public static const PORTRAIT:String = "纵向";

	public static var sizeArray:Array = [PrintPageSize.A4_SIZE, PrintPageSize.B5_SIZE];
	public static var orientationArray:Array = [PrintPageSize.PORTRAIT, PrintPageSize.LANDSCAPE ];

	private var _name:String;
	private var _width:int;
	private var _height:int;

	public function PrintPageSize(name:String, width:int, heigth:int) {
		_name = name;
		_width = width;
		_height = heigth;
	}

	public function get name():String {
		return _name;
	}

	public function set name(value:String):void {
		_name = value;
	}

	public function get width():int {
		return _width;
	}

	public function set width(value:int):void {
		_width = value;
	}

	public function get height():int {
		return _height;
	}

	public function set height(value:int):void {
		_height = value;
	}
}
}
