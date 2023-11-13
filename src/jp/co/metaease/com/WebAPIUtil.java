/*********************************************************************
 * 文字チェック、プロパティファイルよりの値の取得等の部品を提供します。
 * @funcname Webユーティリティ
 * @@
 * WebAPIUtil.java
 * WebAPI　共通部品
 *
 * クラス一覧
 *     No  クラス名                概要
 *     01  WebAPIUtil　　　　　    文字チェック、プロパティファイルよりの値の取得等の部品を提供します。
 *
 * 履歴
 *     No       日付        Ver             更新者              内容
 *     00001    2023/11/09　V0001L00001     Yuki Takahashi     Initial
 *
 * Copyright(C) Metaease 2023
 *
 * @author  Y.Takahashi
 *********************************************************************/
 package jp.co.metaease.com;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
//import java.util.Collection;
//import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;
//import java.util.Random;
import java.util.ResourceBundle;

public class WebAPIUtil {

	/**
	 * 半角英数字の範囲の最初を示します。
	 */
	private static final char HALF_DIGIT_AND_ALPHABET_START = '\u0021';

	/**
	 * 半角英数字の範囲の最後を示します。
	 */
	private static final char HALF_DIGIT_AND_ALPHABET_END = '\u007E';

	/**
	 * 半角カタカナの範囲の最初を示します。
	 */
	private static final char HALF_KATAKANA_START = '\uFF61';

	/**
	 * 半角カタカナの範囲の最後を示します。
	 */
	private static final char HALF_KATAKANA_END = '\uFF9F';

	/**
	 * 半角スペースです。
	 */
	private static final char HALF_SPACE = '\u0020';

	/**
	 * 禁則文字  ("',.\`|~)です。
	 */
	private static final String DISABLE_CHARS = "\"',.\\`~";

	/**
	 * 全角カタカナの範囲の最初を示します。
	 */
	private static final char KATAKANA_START = '\u30A1';

	/**
	 * 全角カタカナの範囲の最後を示します。
	 */
	private static final char KATAKANA_END = '\u30F4';

	/**
	 * 全角アルファベット小文字の範囲の最初を示します。
	 */
	private static final char ALPHA_SMALL_START = '\uFF41';

	/**
	 * 全角アルファベット小文字の範囲の最後を示します。
	 */
	private static final char ALPHA_SMALL_END = '\uFF5A';

	/**
	 * 全角アルファベット大文字の範囲の最初を示します。
	 */
	private static final char ALPHA_LARGE_START = '\uFF21';

	/**
	 * 全角アルファベット大文字の範囲の最後を示します。
	 */
	private static final char ALPHA_LARGE_END = '\uFF3A';

	/**
	 * 全角数字の範囲の最初を示します。
	 */
	private static final char NUM_START = '\uFF10';

	/**
	 * 全角数字の範囲の最後を示します。
	 */
	private static final char NUM_END = '\uFF19';

	/**
	 * 全角ギリシャ大文字の範囲の最初を示します。
	 */
	private static final char ROMA1_START = '\u0391';

	/**
	 * 全角ギリシャ大文字の範囲の最後を示します。
	 */
	private static final char ROMA1_END = '\u03A9';

	/**
	 * 全角ギリシャ小文字の範囲の最初を示します。
	 */
	private static final char ROMA2_START = '\u03B1';

	/**
	 * 全角ギリシャ小文字の範囲の最後を示します。
	 */
	private static final char ROMA2_END = '\u03C9';

	/**
	 * 全角ロシア大文字の範囲の最初を示します。
	 */
	private static final char ROMA3_START = '\u0410';

	/**
	 * 全角ロシア大文字の範囲の最後を示します。
	 */
	private static final char ROMA3_END = '\u042F';

	/**
	 * 全角ロシア小文字の範囲の最初を示します。
	 */
	private static final char ROMA4_START = '\u0430';

	/**
	 * 全角ロシア小文字の範囲の最後を示します。
	 */
	private static final char ROMA4_END = '\u044F';

	/**
	 * 全角記号の許可文字です。
	 * 　、。，．・：；？！゛゜´｀¨＾￣＿ヽヾゝゞ〃仝々〆〇ー―‐／＼～∥｜…‥‘’“”（）〔〕［］｛｝〈〉《》「」『』【】＋
	 * －±×÷＝≠＜＞≦≧∞∴♂♀°′″℃￥＄￠￡％＃＆＊＠§☆★○●◎◇◆□■△▲▽▼※〒→←↑↓〓・・・・・・・・・・・∈
	 * ∋⊆⊇⊂⊃∪∩∧∨￢⇒⇔∀∃∠⊥⌒∂∇≡≒≪≫√∽∝∵∫∬Å‰♯♭♪†‡¶◯
	 */
	private static final String SIGN_CHARS = "\u3000\u3001\u3002\uFF0C\uFF0E\u30FB\uFF1A\uFF1B\uFF1F\uFF01\u309B\u309C"
			+ "\u00B4\uFF40\u00A8\uFF3E\uFFE3\uFF3F\u30FD\u30FE\u309D\u309E\u3003\u4EDD"
			+ "\u3005\u3006\u3007\u30FC\u2015\u2010\uFF0F\uFF3C\uFF5E\u2225\uFF5C\u2026"
			+ "\u2025\u2018\u2019\u201C\u201D\uFF08\uFF09\u3014\u3015\uFF3B\uFF3D\uFF5B"
			+ "\uFF5D\u3008\u3009\u300A\u300B\u300C\u300D\u300E\u300F\u3010\u3011\uFF0B"
			+ "\uFF0D\u00B1\u00D7\u00F7\uFF1D\u2260\uFF1C\uFF1E\u2266\u2267\u221E\u2234"
			+ "\u2642\u2640\u00B0\u2032\u2033\u2103\uFFE5\uFF04\uFFE0\uFFE1\uFF05\uFF03"
			+ "\uFF06\uFF0A\uFF20\u00A7\u2606\u2605\u25CB\u25CF\u25CE\u25C7\u25C6\u25A1"
			+ "\u25A0\u25B3\u25B2\u25BD\u25BC\u203B\u3012\u2192\u2190\u2191\u2193\u3013"
			+ "\u30FB\u30FB\u30FB\u30FB\u30FB\u30FB\u30FB\u30FB\u30FB\u30FB\u30FB\u2208"
			+ "\u220B\u2286\u2287\u2282\u2283\u222A\u2229\u2227\u2228\uFFE2\u21D2\u21D4"
			+ "\u2200\u2203\u2220\u22A5\u2312\u2202\u2207\u2261\u2252\u226A\u226B\u221A"
			+ "\u223D\u221D\u2235\u222B\u222C\u212B\u2030\u266F\u266D\u266A\u2020\u2021"
			+ "\u00B6\u25EF";

	/**
	 * コンストラクターです。
	 * @@
	 * WebAPIUtil
	 * @note
	 * @@
	 */
	public WebAPIUtil() {
		super();
	}

	/**
	 * ascii文字以外が含まれていないかチェックします。
	 * 範囲は(0x21～0x7e)です。
	 * @@
	 * ascii2Chk
	 * @note  半角スペースを含む場合はNG
	 * @param str 文字列
	 * @return 判定結果（true：チェックOK false：チェックNG）
	 * @@
	 */
	public static boolean ascii2Chk(String str) {

		// 引数がnull又は空文字列の場合はfalseを返します。
		if (StringUtil.isEmpty(str)) {
			return false;
		}

		return isNarrowChar(str, false);
	}

	/**
	 * ascii文字以外が含まれていないかチェックします。
	 * 範囲は(0x20～0x7e)です。
	 * @@
	 * asciiChk
	 * @note 半角スペース含む場合もOK
	 * @param str 文字列
	 * @return 判定結果（true：チェックOK false：チェックNG）
	 * @@
	 */
	public static boolean asciiChk(String str) {

		// 引数がnull又は空文字列の場合はfalseを返します。
		if (StringUtil.isEmpty(str)) {
			return false;
		}

		return isNarrowChar(str, true);
	}

	/**
	 * 入力禁止文字のチェックを行います。
	 * 禁止文字は("',.\`|~)です。
	 * @@
	 * hasDisabledChar
	 * @note
	 * @param str 文字列
	 * @return 判定結果（true：チェックOK false：チェックNG）
	 * @@
	 */
	public static boolean hasDisabledChar(String str) {

		// チェック結果です。
		boolean result = false;

		// チェック対象の文字列長です。
		int strLength = str.length();

		// チェック対象の文字列の1文字分を格納します。
		char checkChar = 0x00;

		for (int i = 0; i < strLength; i++) {

			// チェック対象の文字列の1文字分を取得します。
			checkChar = str.charAt(i);

			// 入力禁止文字と同じ文字である場合、trueを返却します。
			if (DISABLE_CHARS.indexOf(checkChar) >= 0) {
				result = true;
				break;
			}

		}

		// チェック結果を返却します。
		return result;
	}

	/**
	 * 半角文字で構成されているかどうかチェックを行います。
	 * @@
	 * isNarrowChar
	 * @note
	 * @param str チェック文字列
	 * @param flg 半角スペースの可負荷
	 * @return 判定結果（true：チェックOK false：チェックNG）
	 * @@
	 */
	private static boolean isNarrowChar(String str, boolean flg) {

		// 引数がnull又は空文字列の場合はfalseを返します。
		if (StringUtil.isEmpty(str)) {
			return false;
		}

		// 引数の文字列を文字に分解します。
		char chars[] = str.toCharArray();
		int length = chars.length;

		// 文字列を構成する文字が半角文字の範囲に入っているか調べます。
		for (int i = 0; i < length; i++) {

			// 範囲に入っていない場合は false を返します。
			if (!(HALF_DIGIT_AND_ALPHABET_START <= chars[i] && chars[i] <= HALF_DIGIT_AND_ALPHABET_END)) {

				// 半角スペースチェックする場合
				if (flg) {

					// 半角スペースを許可する場合
					if (chars[i] == HALF_SPACE) {
						continue;
					}

				}
				return false;
			}
		}
		return true;
	}

	/**
	 * 生年月日と現在日から、年齢を計算します。
	 * @@
	 * calcAge
	 * @note
	 * @param byyyymmdd 生年月日の日付文字列
	 * @param yyyymmdd 現在日の日付文字列
	 * @return 年齢
	 * @@
	 */
	public static Integer calcAge(String byyyymmdd, String yyyymmdd) {

		// 引数がnull又は空文字列の場合はnullを返します。
		if (StringUtil.isEmpty(byyyymmdd) || StringUtil.isEmpty(yyyymmdd)) {
			return null;
		}

		// 引数が8桁で無い場合はnullを返す。
		if (byyyymmdd.length() != 8 || yyyymmdd.length() != 8) {
			return null;
		}

		// 数値でない場合はnullを返します。
		try {
			Integer.parseInt(byyyymmdd);
			Integer.parseInt(yyyymmdd);
		} catch (NumberFormatException e) {
			return null;
		}

		try {
			//日付を年、月、日に分割します。
			int byyyy = Integer.parseInt(byyyymmdd.substring(0, 4));
			int bmm = Integer.parseInt(byyyymmdd.substring(4, 6));
			int bdd = Integer.parseInt(byyyymmdd.substring(6));
			int yyyy = Integer.parseInt(yyyymmdd.substring(0, 4));
			int mm = Integer.parseInt(yyyymmdd.substring(4, 6));
			int dd = Integer.parseInt(yyyymmdd.substring(6));

			//現在の年から生年月日の年を引きます。
			int ret = yyyy - byyyy;

			// 現在の月が生年月日の月より小さい場合
			if (mm < bmm) {
				// まだ誕生日を迎えていないので1歳引きます。
				ret = ret - 1;
			}
			// 現在の月が生年月日の月と同じでかつ現在の日が生年月日の日より小さい場合
			if ((mm == bmm) && (dd < bdd)) {
				// まだ誕生日を迎えていないので1歳引きます。
				ret = ret - 1;
			}

			// 求めた年齢を返却します。
			return (Integer)Integer.valueOf(ret);

		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * 数値を3桁のカンマ区切に編集します。
	 * @@
	 * convertYenString
	 * @note  マイナス値、小数にも対応しています。
	 * @param price 金額
	 * @return 3桁カンマ区切にした文字列
	 * @@
	 */
	public static String convertYenString(BigDecimal price) {

		// パラメタがnull場合nullを返却します。
		if (price == null) {
			return null;
		}

		// 符号の一時保持フィールド
		String sFugou = "";
		// 小数以下の一時保持フィールド
		String sSyousu = "";

		// 戻り値の文字列を格納します。
		StringBuffer sbPrice = new StringBuffer();

		String sPrice = price.toString();

		// 文字列の1文字目を取得します。
		char cTemp = sPrice.charAt(0);

		// 取得した文字がマイナス【-】の場合です。
		if (cTemp == '-') {
			// 符号の一時保持フィールドに保持します。
			sFugou = sPrice.substring(0, 1);
			// 符号を外した物を再格納します。
			sPrice = sPrice.substring(1, sPrice.length());
		}

		// 小数点の位置を取得します。
		int iIndex = sPrice.indexOf(".");

		// 小数が存在した場合
		if (iIndex != -1) {
			// 小数の一時保持フィールドに保持します。
			sSyousu = sPrice.substring(iIndex, sPrice.length());
			// 小数以下を外した物を再格納します。
			sPrice = sPrice.substring(0, iIndex);
		}

		// 戻り値格納用フィールドに符号を設定します。
		sbPrice.append(sFugou);
		// 整数部が3桁以上ある場合
		if (sPrice.length() > 3) {
			// 3で除算し余りを求めます。
			int iPiriodIndex = sPrice.length() % 3;
			// 余りが0でない場合
			if (iPiriodIndex != 0) {
				sbPrice.append(sPrice.substring(0, iPiriodIndex));
				sPrice = sPrice.substring(iPiriodIndex, sPrice.length());
				sbPrice.append(",");
			}
			// 整数部が3桁以下になるまで繰り返します。
			while (sPrice.length() > 3) {
				sbPrice.append(sPrice.substring(0, 3));
				sPrice = sPrice.substring(3, sPrice.length());
				sbPrice.append(",");
			}
		}
		sbPrice.append(sPrice);
		// 小数部を付加します。
		sbPrice.append(sSyousu);

		return sbPrice.toString();
	}

	/**
	 * 文字列が全て全角で構成されているかどうかチェックします。
	 * @@
	 * zenkakuChk
	 * @note
	 * @param str チェック対象文字列
	 * @return 判定結果（true：チェックOK false：チェックNG）
	 * @@
	 */
	public static boolean zenkakuChk(String str) {

		// 引数がnull又は空文字列の場合はfalseを返す
		if (StringUtil.isEmpty(str)) {
			return false;
		}

		// 引数の文字列を文字に分解します。
		char chars[] = str.toCharArray();
		int length = chars.length;

		// 文字列を構成する文字が全角文字の範囲に入っているか調べます。
		for (int i = 0; i < length; i++) {

			// 範囲に入っていない場合は false を返します。
			if ((HALF_DIGIT_AND_ALPHABET_START <= chars[i] && chars[i] <= HALF_DIGIT_AND_ALPHABET_END)
					|| (HALF_KATAKANA_START <= chars[i] && chars[i] <= HALF_KATAKANA_END)
					|| chars[i] == HALF_SPACE) {
				return false;
			}
		}
		return true;

	}

	/**
	 * 文字列が全て全角カナで構成されているかどうかチェックします。
	 * @@
	 * zenkakuKana2Chk
	 * @note
	 * @param str チェック対象文字列
	 * @return 判定結果（true：チェックOK false：チェックNG）
	 * @@
	 */
	public static boolean zenkakuKana2Chk(String str) {
		// 引数がnull又は空文字列の場合はfalseを返す
		if (StringUtil.isEmpty(str)) {
			return false;
		}

		// 引数の文字列を文字に分解します。
		char chars[] = str.toCharArray();
		int length = chars.length;

		// 文字列を構成する文字が全角カタカナ文字の範囲に入っているか調べます。
		for (int i = 0; i < length; i++) {

			// 範囲に入っていない場合は false を返します。
			if (!((chars[i] >= KATAKANA_START && chars[i] <= KATAKANA_END)
					|| (chars[i] >= ALPHA_SMALL_START && chars[i] <= ALPHA_SMALL_END)
					|| (chars[i] >= ALPHA_LARGE_START && chars[i] <= ALPHA_LARGE_END)
					|| (chars[i] >= NUM_START && chars[i] <= NUM_END)
					|| (chars[i] >= ROMA1_START && chars[i] <= ROMA1_END)
					|| (chars[i] >= ROMA2_START && chars[i] <= ROMA2_END)
					|| (chars[i] >= ROMA3_START && chars[i] <= ROMA3_END)
					|| (chars[i] >= ROMA4_START && chars[i] <= ROMA4_END) || SIGN_CHARS
					.indexOf(chars[i]) >= 0)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 指定されたプロパティファイルよりプロパティの一覧を返却します。
	 * @@
	 * getPropertyValueIchiran
	 * @note
	 * @param filePath プロパティファイル名(.propertyをは必要なし)
	 * @return プロパティファイルの一覧
	 * @@
	 */
	@SuppressWarnings({
		"rawtypes", "unchecked"
	})
	public static HashMap getPropertyValueIchiran(String filePath) {

		// 引数がnull又は空文字列の場合はnullを返す
		if (StringUtil.isEmpty(filePath)) {
			return null;
		}

		HashMap retHash = new HashMap();

		try {
			// プロパティファイル名を元にプロパティリソースバンドルを作成します。
			ResourceBundle bundle = ResourceBundle.getBundle(
					filePath,
					Locale.JAPANESE);
			// プロパティリソースバンドルよりキーのリストを取得します。
			Enumeration enu = bundle.getKeys();

			String key;

			while (enu.hasMoreElements()) {
				// プロパティのキー
				key = enu.nextElement().toString();
				// HashMapに値を格納します。
				retHash.put(key, bundle.getString(key));
			}
		} catch (MissingResourceException ex) {
			return null;
		}
		return retHash;
	}

	/**
	 * 指定されたプロパティファイルより指定されたキーの値を返却します。
	 * @@
	 * getPropertyValue
	 * @note
	 * @param filePath プロパティファイル名(.propertyは必要なし)
	 * @param key 取得したい項目のキー
	 * @return プロパティファイルより取得した値
	 * @@
	 */
	public static String getPropertyValue(String filePath, String key) {

		// 引数がnull又は空文字列の場合はnullを返す
		if (StringUtil.isEmpty(filePath) || StringUtil.isEmpty(key)) {
			return null;
		}

		try {
			// プロパティファイル名を元にプロパティリソースバンドルを作成します。
			ResourceBundle bundle = ResourceBundle.getBundle(
					filePath,
					Locale.JAPANESE);
			// プロパティリソースバンドルよりキーで値を取得し返却します。
			return bundle.getString(key);
		} catch (MissingResourceException ex) {
			return null;
		}
	}

	/**
	 * 指定されたプロパティファイルより指定されたキーの値を返却します。
	 * @@
	 * getPropertyValue
	 * @note
	 * @param filePath プロパティファイル名(.propertyは必要なし)
	 * @param key 取得したい項目のキー
	 * @param messageId エラーメッセージID
	 * @return プロパティファイルより取得した値
	 * @throws Exception 
	 * @@
	 */
	public static String getPropertyValue(String filePath, String key, String messageId)
			throws Exception {

		// 引数がnull又は空文字列の場合はnullを返す
		if (StringUtil.isEmpty(filePath) || StringUtil.isEmpty(key)) {
			return null;
		}

		try {
			// プロパティファイル名を元にプロパティリソースバンドルを作成します。
			ResourceBundle bundle = ResourceBundle.getBundle(
					filePath,
					Locale.JAPANESE);
			// プロパティリソースバンドルよりキーで値を取得し返却します。
			return bundle.getString(key);
		} catch (MissingResourceException ex) {
			throw new Exception(messageId, ex);
		}
	}


	/**
	 * Eメールアドレスの＠存在、．存在、@複数存在チェックを行います。
	 * @@
	 * isEMailInputCheck
	 * @note
	 * @param eMail Eメールアドレス
	 * @param checkflg チェック用のフラグ(true：エラーあり、false：エラーなし)
	 * @return true：正常、false：エラー
	 * @@
	 */
	public static boolean isEMailInputCheck(String eMail, boolean checkflg) {

		System.out.println("■ Ｅメール　　　：" + eMail);
		System.out.println("■ チェックフラグ：" + checkflg);

		// チェックフラグが「false：エラーあり」の場合以降の処理は行わない。
		if (!checkflg) {
			return checkflg;
		}

		// Eメールが設定されている場合
		if (!StringUtil.isEmpty(eMail)) {

			// @の文字が含まれていない場合はエラーとする。
			if (eMail.indexOf("@") <= 0) {
				System.out.println("☆☆ @が含まれていません。");

				return false;
			}

			// 'の文字が含まれている場合はエラーとする。
			if (eMail.indexOf("'") >= 0) {
				System.out.println("☆☆ 'が含まれています。");

				return false;
			}

			// "の文字が含まれている場合はエラーとする。
			if (eMail.indexOf("\"") >= 0) {
				System.out.println("☆☆ \"が含まれています。");

				return false;
			}

			// @の位置を取得
			int index = eMail.indexOf("@");
			// @以降の文字列を取得
			String eMailMoji1 = eMail.substring(index, eMail.length());

			// .が含まれていない場合エラーとする。
			if (eMailMoji1.indexOf(".") <= 0) {
				System.out.println("☆☆ .が含まれていません。");

				return false;
			}

			// @より後の文字列を取得
			String eMailMoji2 = eMail.substring(index + 1, eMail.length());

			// @の文字が含まれている場合はエラーとする。
			if (eMailMoji2.indexOf("@") >= 0) {
				System.out.println("☆☆ @が含まれています。");

				return false;
			}

			// 「．」存在チェック（ドメイン内に"．"が連続して存在する場合はエラー）
			if (eMailMoji2.indexOf("..") >= 0) {
				System.out.println("☆☆ドメイン内に「.」が連続して存在する。");
				return false;
			}
			// 「．」存在チェック（ドメインの先頭に「.」が存在する場合はエラー）
			String str = eMail.substring(index + 1, index + 2);
			if (".".equals(str)) {
				System.out.println("☆☆ ドメインの先頭に「.」が存在する");
				return false;
			}
		}

		return true;
	}

	/**
	 * パスワードに特殊文字が含まれているかチェックします。
	 * @@
	 * isPasWrdTokushumojiCheck
	 * @note
	 * @param pasWrd パスワード
	 * @return true：含まれている、false：含まれていない
	 * @@
	 */
	public static boolean isPasWrdTokushumojiCheck(String pasWrd) {

		System.out.println("■ パスワード：" + pasWrd);

		// 引数の文字列を文字に分解します。
		char chars[] = pasWrd.toCharArray();

		// 文字の数分ループ
		for (int i = 0; i < chars.length; i++) {

			// 特殊文字が含まれているかチェックを行います。
			switch (chars[i]) {

			case ' ':
                System.out.println("☆☆ スペースが含まれています。");
				return true;

			case '"':
                System.out.println("☆☆ \"が含まれています。");
				return true;

			case '\'':
                System.out.println("☆☆ 'が含まれています。");
				return true;

			case ',':
                System.out.println("☆☆ ,が含まれています。");
				return true;

			case '.':
                System.out.println("☆☆ .が含まれています。");
				return true;

			case '\\':
                System.out.println("☆☆ \\が含まれています。");
				return true;

			case '`':
                System.out.println("☆☆ `が含まれています。");
				return true;

			case '|':
                System.out.println("☆☆ |が含まれています。");
				return true;

			case '~':
                System.out.println("☆☆ ~が含まれています。");
				return true;

			default:
				break;
			}

		}

		return false;
	}

	/**
	 * 数値だけで構成されているかチェックします。
	 * @@
	 * isNumberCheck
	 * @note
	 * @param count 数値
	 * @return true：チェックOK、false：チェックNG
	 */
	public static boolean isNumberCheck(String str) {

		// 引数がnull又は空文字列の場合はfalseを返します。
		if (StringUtil.isEmpty(str)) {
			return false;
		}
		return str.matches("[0-9]*");
	}

	/**
	 * 制御コードが含まれていないことをチェックします。
	 * @@
	 * isNumberCheck
	 * @note
	 * @param str 文字列
	 * @return true：チェックOK、false：チェックNG
	 */
	public static boolean isCtrlCodeChk(String str) {
		byte bytes[] = str.getBytes();
		for (int i = 0; i < bytes.length; i++) {
			// 制御コード（0x00～0x1F）が含まれる場合はfalseを返す。
			// ※0x09（水平タブ）、0x0A（改行）、0x0D（復帰）は除外
			if ((byte) 0x00 <= bytes[i]
					&& bytes[i] <= (byte) 0x1F
					&& bytes[i] != (byte) 0x09
					&& bytes[i] != (byte) 0x0A
					&& bytes[i] != (byte) 0x0D) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 年から閏年を判定する
	 * @@
	 * isDragonYear
	 * @note
	 * @param year 年
	 * @return ture：閏年、false：閏年でない
	 */
	public static boolean isDragonYear(int year) {

		if ((year % 4) != 0) {
			return false;
		} else {
			if ((year % 100) != 0) {
				return true;
			} else {
				if ((year % 400) != 0) {
					return false;
				} else {
					return true;
				}
			}
		}
	}

	/**
	 * Eメールアドレスのローカル部(@以前)に何か設定されているかチェックします。
	 * @@
	 * localEmptyChk
	 * @param str 文字列
	 * @return 判定結果（true：チェックOK false：チェックNG）
	 * @@
	 */
	public static boolean localEmptyChk(String str) {

		// Eメールが設定されている場合
		if (!StringUtil.isEmpty(str)) {

			// @の位置を取得
			int index = str.indexOf("@");

			// @より後の文字列を取得
			String eMailMoji1 = str.substring(0, index);

			// ドメイン部(@以降)に何も設定されていない場合はエラーとする。
			if (StringUtil.isEmpty(eMailMoji1)) {
				System.out.println("☆☆ ローカル部(@以前)に何も設定されていません。");
				return false;
			}
		}

		return true;
	}

	/**
	 * Eメールアドレスのローカル部(@以前)の先頭に「.」が設定されていないかチェックします。
	 * @@
	 * localTopDotChk
	 * @param str 文字列
	 * @return 判定結果（true：チェックOK false：チェックNG）
	 * @@
	 */
	public static boolean localTopDotChk(String str) {

		// Eメールが設定されている場合
		if (!StringUtil.isEmpty(str)) {

			// @の位置を取得
			int index = str.indexOf("@");

			// @以前の文字列を取得
			String eMailMoji1 = str.substring(0, index);

			// ローカル部(@以前)の先頭に「.」が設定されている場合はエラーとする。
			if (eMailMoji1.indexOf(".") == 0) {
				System.out.println("☆☆ ローカル部(@以前)の先頭に「.」が設定されています。");
				return false;
			}
		}

		return true;
	}

	/**
	 * ローカル部(@以前)の末尾に「.」が設定されていないかチェックします。
	 * @@
	 * localLastDotChk
	 * @param str 文字列
	 * @return 判定結果（true：チェックOK false：チェックNG）
	 * @@
	 */
	public static boolean localLastDotChk(String str) {

		// Eメールが設定されている場合
		if (!StringUtil.isEmpty(str)) {

			// @の位置を取得
			int index = str.indexOf("@");

			// @以前の文字列を取得
			String eMailMoji1 = str.substring(0, index);

			// ローカル部の文字列長を取得
			int len = eMailMoji1.length();

			if (len != 0) {

				// ローカル部(@以前)の末尾に「.」が設定されている場合はエラーとする。
				if (eMailMoji1.indexOf(".") == len - 1) {
					System.out.println("☆☆ ローカル部(@以前)に「.」が設定されています。");
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * ローカル部(@以前)の先頭に「-」が設定されていないかチェックします。
	 * @@
	 * localTopHyphenChk
	 * @param str 文字列
	 * @return 判定結果（true：チェックOK false：チェックNG）
	 * @@
	 */
	public static boolean localTopHyphenChk(String str) {

		// Eメールが設定されている場合
		if (!StringUtil.isEmpty(str)) {

			// @の位置を取得
			int index = str.indexOf("@");

			// @以前の文字列を取得
			String eMailMoji1 = str.substring(0, index);

			// ローカル部(@以前)の先頭に「-」が設定されている場合はエラーとする。
			if (eMailMoji1.indexOf("-") == 0) {
				System.out.println("☆☆ ローカル部(@以前)に「-」が設定されています。");
				return false;
			}
		}

		return true;
	}

	/**
	 * ローカル部(@以前)に「[」が設定されていないかチェックします。
	 * @@
	 * localLeftSquareBracketChk
	 * @param str 文字列
	 * @return 判定結果（true：チェックOK false：チェックNG）
	 * @@
	 */
	public static boolean localLeftSquareBracketChk(String str) {

		// Eメールが設定されている場合
		if (!StringUtil.isEmpty(str)) {

			// @の位置を取得
			int index = str.indexOf("@");

			// @以前の文字列を取得
			String eMailMoji1 = str.substring(0, index);

			// ローカル部(@以前)に「[」が設定されている場合はエラーとする。
			if (eMailMoji1.indexOf("[") >= 0) {
				System.out.println("☆☆ ローカル部(@以前)に「[」が設定されています。");
				return false;
			}
		}

		return true;
	}

	/**
	 * ローカル部(@以前)に「]」が設定されていないかチェックします。
	 * @@
	 * localRightSquareBracketChk
	 * @param str 文字列
	 * @return 判定結果（true：チェックOK false：チェックNG）
	 * @@
	 */
	public static boolean localRightSquareBracketChk(String str) {

		// Eメールが設定されている場合
		if (!StringUtil.isEmpty(str)) {

			// @の位置を取得
			int index = str.indexOf("@");

			// @以前の文字列を取得
			String eMailMoji1 = str.substring(0, index);

			// ローカル部(@以前)に「]」が設定されている場合はエラーとする。
			if (eMailMoji1.indexOf("]") >= 0) {
				System.out.println("☆☆ ローカル部(@以前)に「]」が設定されています。");
				return false;
			}
		}

		return true;
	}

	/**
	 * ドメイン部(@以降)に何か設定されているかチェックします。
	 * @@
	 * domeinEmptyChk
	 * @param str 文字列
	 * @return 判定結果（true：チェックOK false：チェックNG）
	 * @@
	 */
	public static boolean domeinEmptyChk(String str) {

		// Eメールが設定されている場合
		if (!StringUtil.isEmpty(str)) {

			// @の位置を取得
			int index = str.indexOf("@");

			// @より後の文字列を取得
			String eMailMoji2 = str.substring(index + 1, str.length());

			// ドメイン部(@以降)に何も設定されていない場合はエラーとする。
			if (StringUtil.isEmpty(eMailMoji2)) {
				System.out.println("☆☆ ドメイン部(@以降)に何も設定されていません。");
				return false;
			}
		}

		return true;
	}

	/**
	 * ドメイン部(@以降)の先頭に「.」が設定されていないかチェックします。
	 * @@
	 * domeinTopDotChk
	 * @note
	 * @param eMail Eメールアドレス
	 * @return true：正常、false：エラー
	 * @@
	 */
	public static boolean domeinTopDotChk(String eMail) {

		// Eメールが設定されている場合
		if (!StringUtil.isEmpty(eMail)) {

			// @の位置を取得
			int index = eMail.indexOf("@");

			// @より後の文字列を取得
			@SuppressWarnings("unused")
			String eMailMoji2 = eMail.substring(index + 1, eMail.length());

			// 「．」存在チェック（ドメインの先頭に「.」が存在する場合はエラー）
			String str = eMail.substring(index + 1, index + 2);
			if (".".equals(str)) {
				System.out.println("☆☆ ドメイン部(@以降)の先頭に「.」の文字が設定されています。");
				return false;
			}
		}

		return true;
	}

	/**
	 * ドメイン部(@以降)に「.」が設定されているかチェックします。
	 * @@
	 * domeinTopDotChk
	 * @note
	 * @param eMail Eメールアドレス
	 * @return true：正常、false：エラー
	 * @@
	 */
	public static boolean domeinDotChk(String eMail) {

		// Eメールが設定されている場合
		if (!StringUtil.isEmpty(eMail)) {

			// @の位置を取得
			int index = eMail.indexOf("@");

			// @より後の文字列を取得
			String eMailMoji2 = eMail.substring(index + 1, eMail.length());

			// ドメイン部(@以降)に「.」の文字が含まれていない場合はエラーとする。
			// 20110511 システム更改WEB課題 PT障害対応 (障害No.1) start
			//if (eMailMoji2.indexOf(".") <= 0) {
			if (eMailMoji2.indexOf(".") < 0) {
				// 20110511 システム更改WEB課題 PT障害対応 (障害No.1) end
				System.out.println("☆☆ ドメイン部(@以降)に「.」の文字が設定されていません。");
				return false;
			}
		}

		return true;
	}

	/**
	 * ドメイン部(@以降)で「[ ]」が対で設定されているかチェックします。
	 * @@
	 * domeinSquareBracketChk
	 * @param str 文字列
	 * @return 判定結果（true：チェックOK false：チェックNG）
	 * @@
	 */
	public static boolean domeinSquareBracketChk(String str) {

		// Eメールが設定されている場合
		if (!StringUtil.isEmpty(str)) {

			// @の位置を取得
			int index = str.indexOf("@");

			// @より後の文字列を取得
			String eMailMoji2 = str.substring(index + 1, str.length());

			// ドメイン部(@以降)で「[ ]」が対で設定されていない場合はエラーとする。
			if (eMailMoji2.indexOf("[") >= 0 && eMailMoji2.indexOf("]") < 0) {

				return false;
			}

			if (eMailMoji2.indexOf("]") >= 0 && eMailMoji2.indexOf("[") < 0) {
				System.out.println("☆☆ ドメイン部(@以降)で「[ ]」が対で設定されていません。");
				return false;
			}
		}

		return true;
	}

	/**
	 * ドメイン部(@以降)で「[ ]」が先頭と末尾以外に設定されていないかチェックします。
	 * @@
	 * domeinSquareBracketTLChk
	 * @param str 文字列
	 * @return 判定結果（true：チェックOK false：チェックNG）
	 * @@
	 */
	public static boolean domeinSquareBracketTLChk(String str) {

		// Eメールが設定されている場合
		if (!StringUtil.isEmpty(str)) {

			// @の位置を取得
			int index = str.indexOf("@");

			// @より後の文字列を取得
			String eMailMoji2 = str.substring(index + 1, str.length());

			// ドメイン部(@以降)で「[ ]」が対で設定されている場合のみ以下のチェックを行う。
			if (eMailMoji2.indexOf("[") >= 0 && eMailMoji2.indexOf("]") >= 0) {

				// ドメイン部(@以降)で「[ ]」が先頭と末尾以外に設定されている場合はエラーとする。
				if (eMailMoji2.indexOf("[") != 0
						|| eMailMoji2.indexOf("]") != eMailMoji2.length() - 1) {
                    System.out.println("☆☆ ドメイン部(@以降)で「[ ]」が先頭と末尾以外に設定されています。");
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * ドメイン部(@以降)で「[ ]」内に数字と「.」以外が設定されていないかチェックします。
	 * @@
	 * domeinSquareBracketNumberDotChk
	 * @param str 文字列
	 * @return 判定結果（true：チェックOK false：チェックNG）
	 * @@
	 */
	public static boolean domeinSquareBracketNumberDotChk(String str) {

		// Eメールが設定されている場合
		if (!StringUtil.isEmpty(str)) {

			// @の位置を取得
			int index = str.indexOf("@");

			// @より後の文字列を取得
			String eMailMoji2 = str.substring(index + 1, str.length());

			// ドメイン部(@以降)で「[ ]」が対で設定されている場合のみ以下のチェックを行う。
			if (eMailMoji2.indexOf("[") >= 0 && eMailMoji2.indexOf("]") >= 0) {

				// 「[ ]」内の文字列を取得
				String eMailMoji3 = eMailMoji2.substring(
						eMailMoji2.indexOf("[") + 1,
						eMailMoji2.indexOf("]"));

				// ドメイン部(@以降)で「[ ]」内に数字と「.」以外が設定されている場合はエラーとする。
				if (!eMailMoji3
						.matches("(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})")) {
                    System.out.println("☆☆ ドメイン部(@以降)で「[ ]」内に数字と「.」以外が設定されています。");
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Eメールアドレスの「＠」存在チェックを行います。
	 * @@
	 * commercialChk
	 * @note
	 * @param eMail Eメールアドレス
	 * @return true：正常、false：エラー
	 * @@
	 */
	public static boolean commercialChk(String eMail) {

		// Eメールが設定されている場合
		if (!StringUtil.isEmpty(eMail)) {

			// @の位置を取得
			int index = eMail.indexOf("@");

			// @の文字が含まれていない場合はエラーとする。
			if (index < 0) {

				return false;
			}

			// @より後の文字列を取得
			String eMailMoji1 = eMail.substring(index + 1, eMail.length());

			// @の文字が複数含まれている場合はエラーとする。
			if (eMailMoji1.indexOf("@") >= 0) {
				System.out.println("☆☆ @複数が含まれています。");
				return false;
			}
		}

		return true;
	}

	/**
	 * 「(」が設定されていないかチェックします。
	 * @@
	 * leftParenthesisChk
	 * @param str 文字列
	 * @return 判定結果（true：チェックOK false：チェックNG）
	 * @@
	 */
	public static boolean leftParenthesisChk(String str) {

		// Eメールが設定されている場合
		if (!StringUtil.isEmpty(str)) {

			// 「(」が設定されている場合はエラーとする。
			if (str.indexOf("(") >= 0) {
				System.out.println("☆☆ (が含まれています。");
				return false;
			}
		}

		return true;
	}

	/**
	 * 「)」が設定されていないかチェックします。
	 * @@
	 * rightParenthesisChk
	 * @param str 文字列
	 * @return 判定結果（true：チェックOK false：チェックNG）
	 * @@
	 */
	public static boolean rightParenthesisChk(String str) {

		// Eメールが設定されている場合
		if (!StringUtil.isEmpty(str)) {

			// 「)」が設定されている場合はエラーとする。
			if (str.indexOf(")") >= 0) {
				System.out.println("☆☆ )が含まれています。");
				return false;
			}
		}

		return true;
	}

	/**
	 * 「<」が設定されていないかチェックします。
	 * @@
	 * lessThanSignChk
	 * @param str 文字列
	 * @return 判定結果（true：チェックOK false：チェックNG）
	 * @@
	 */
	public static boolean lessThanSignChk(String str) {

		// Eメールが設定されている場合
		if (!StringUtil.isEmpty(str)) {

			// 「<」が設定されている場合はエラーとする。
			if (str.indexOf("<") >= 0) {
				System.out.println("☆☆ <が含まれています。");
				return false;
			}
		}

		return true;
	}

	/**
	 * 「>」が設定されていないかチェックします。
	 * @@
	 * greaterThanSignChk
	 * @param str 文字列
	 * @return 判定結果（true：チェックOK false：チェックNG）
	 * @@
	 */
	public static boolean greaterThanSignChk(String str) {

		// Eメールが設定されている場合
		if (!StringUtil.isEmpty(str)) {

			// 「>」が設定されている場合はエラーとする。
			if (str.indexOf(">") >= 0) {
				System.out.println("☆☆ >が含まれています。");
				return false;
			}
		}

		return true;
	}

	/**
	 * 「,」が設定されていないかチェックします。
	 * @@
	 * commaChk
	 * @param str 文字列
	 * @return 判定結果（true：チェックOK false：チェックNG）
	 * @@
	 */
	public static boolean commaChk(String str) {

		// Eメールが設定されている場合
		if (!StringUtil.isEmpty(str)) {

			// 「,」が設定されている場合はエラーとする。
			if (str.indexOf(",") >= 0) {
				System.out.println("☆☆ ,が含まれています。");
				return false;
			}
		}

		return true;
	}

	/**
	 * 「;」が設定されていないかチェックします。
	 * @@
	 * semicolonChk
	 * @param str 文字列
	 * @return 判定結果（true：チェックOK false：チェックNG）
	 * @@
	 */
	public static boolean semicolonChk(String str) {

		// Eメールが設定されている場合
		if (!StringUtil.isEmpty(str)) {

			// 「;」が設定されている場合はエラーとする。
			if (str.indexOf(";") >= 0) {
				System.out.println("☆☆ ;が含まれています。");
				return false;
			}
		}

		return true;
	}

	/**
	 * 「:」が設定されていないかチェックします。
	 * @@
	 * colonChk
	 * @param str 文字列
	 * @return 判定結果（true：チェックOK false：チェックNG）
	 * @@
	 */
	public static boolean colonChk(String str) {

		// Eメールが設定されている場合
		if (!StringUtil.isEmpty(str)) {

			// 「:」が設定されている場合はエラーとする。
			if (str.indexOf(":") >= 0) {
				System.out.println("☆☆ :が含まれています。");
				return false;
			}
		}

		return true;
	}

	/**
	 * 「\」が設定されていないかチェックします。
	 * @@
	 * yenSignChk
	 * @param str 文字列
	 * @return 判定結果（true：チェックOK false：チェックNG）
	 * @@
	 */
	public static boolean yenSignChk(String str) {

		// Eメールが設定されている場合
		if (!StringUtil.isEmpty(str)) {

			// 「\」が設定されている場合はエラーとする。
			if (str.indexOf("\\") >= 0) {
				System.out.println("☆☆ \\が含まれています。");
				return false;
			}
		}

		return true;
	}

	/**
	 * Eメールアドレスに「'」が設定されていないかチェックします。
	 * @@
	 * singleQuoteChk
	 * @note
	 * @param eMail Eメールアドレス
	 * @return true：正常、false：エラー
	 * @@
	 */
	public static boolean singleQuoteChk(String eMail) {

		// Eメールが設定されている場合
		if (!StringUtil.isEmpty(eMail)) {

			// 'の文字が含まれている場合はエラーとする。
			if (eMail.indexOf("'") >= 0) {
				System.out.println("☆☆ 'が含まれています。");

				return false;
			}
		}

		return true;
	}

	/**
	 * Eメールアドレスに「"」が設定されていないかチェックします。
	 * @@
	 * doubleQuotationChk
	 * @note
	 * @param eMail Eメールアドレス
	 * @return true：正常、false：エラー
	 * @@
	 */
	public static boolean doubleQuotationChk(String eMail) {

		// Eメールが設定されている場合
		if (!StringUtil.isEmpty(eMail)) {

			// "の文字が含まれている場合はエラーとする。
			if (eMail.indexOf("\"") >= 0) {
				System.out.println("☆☆ \"が含まれています。");

				return false;
			}
		}

		return true;
	}

	/**
	 * Eメールアドレスの「.」存在チェックを行います。
	 * @@
	 * dotChk
	 * @note
	 * @param eMail Eメールアドレス
	 * @return true：正常、false：エラー
	 * @@
	 */
	public static boolean dotChk(String eMail) {

		// Eメールが設定されている場合
		if (!StringUtil.isEmpty(eMail)) {

			// @の位置を取得
			int index = eMail.indexOf("@");

			// @以降の文字列を取得
			String eMailMoji1 = eMail.substring(index, eMail.length());

			// .が含まれていない場合エラーとする。
			if (eMailMoji1.indexOf(".") <= 0) {
				System.out.println("☆☆ .が含まれていません。");

				return false;
			}
		}

		return true;
	}

	/**
	 * 「.」が複数連続で設定されていないかチェックします。
	 * @@
	 * continueDotChk
	 * @param str 文字列
	 * @return 判定結果（true：チェックOK false：チェックNG）
	 * @@
	 */
	public static boolean continueDotChk(String str) {

		// Eメールが設定されている場合
		if (!StringUtil.isEmpty(str)) {

			// ローカル部(@以前)に「.」が複数連続で設定されている場合はエラーとする。
			if (str.indexOf("..") >= 0) {
				System.out.println("☆☆ ..が含まれています。");
				return false;
			}
		}

		return true;
	}

	/**
	 * スペース設定されていないかチェックします。
	 * @@
	 * spaceChk
	 * @param str 文字列
	 * @return 判定結果（true：チェックOK false：チェックNG）
	 * @@
	 */
	public static boolean spaceChk(String str) {

		// Eメールが設定されている場合
		if (!StringUtil.isEmpty(str)) {

			// スペースが設定されている場合はエラーとする。
			if (str.indexOf(" ") >= 0) {
				System.out.println("☆☆ スペースが含まれています。");
				return false;
			}
		}

		return true;
	}


    /**
	 * 特殊文字が含まれているかチェックします。
	 * @@
	 * isTokushumojiCheck
	 * @note
	 * @param str チェック対象文字列
	 * @return true：含まれている、false：含まれていない
	 * @@
	 */
	public static boolean isTokushumojiCheck(String str) {

		if (StringUtil.isEmpty(str)) {

			return false;
		}

		// 引数の文字列を文字に分解します。
		char chars[] = str.toCharArray();

		// 文字の数分ループ
		for (int i = 0; i < chars.length; i++) {

			// 特殊文字が含まれているかチェックを行います。
			switch (chars[i]) {

			case '<':

				return true;

			case '>':

				return true;

			case '&':

				return true;

			case '"':

				return true;

			case '\'':

				return true;

			default:
				break;
			}

		}

		return false;
	}

	/**
	 * 機種依存文字を含むかチェックします。
	 *
	 * @param str チェック対象文字列
	 * @return true：含まれている、false：含まれていない
	 * @throws UnsupportedEncodingException
	 */
	public static boolean hasMachineCharacters(String str) {

		if (StringUtil.isEmpty(str)) {

			return false;
		}

		byte charArray[];

		try {
			charArray = str.getBytes("MS932");

			for (int i = 0; i < charArray.length; i++) {
				int charByte = charArray[i] & 0xFF;

				if (isLeadByte(charByte)) {
					// charByte が２バイト文字の第１バイトの場合
					if (++i >= charArray.length) {
						// 第２バイトが存在しない場合：エラー
						return true;
					}
					int charByte2 = charArray[i] & 0xFF;
					if (!isTrailByte(charByte2)) {
						// 第２バイトが不正：エラー
						return true;
					}

					int targetChar = (charByte << 8) | charByte2;

					if ((0x8740 <= targetChar) && (targetChar <= 0x879E)) {
						// 13区 (NEC特殊文字)：機種依存 > Windowsでは表示できるMacで文字化け
						return true;
					}
					if ((0xED40 <= targetChar) && (targetChar <= 0xEFFC)) {
						// 89-92区 (NEC選定IBM拡張文字)：機種依存 > 句点コード
						return true;
					}
					if ((0xFA40 <= targetChar) && (targetChar <= 0xfC4B)) {
						// 115-119区 (IBM拡張文字)：機種依存
						return true;
					}
				}
			}

		} catch (UnsupportedEncodingException e) {

			return false;
		}
		return false;
	}

	/**
	 * 半角数字のみで構成されているかチェックします。
	 *
	 * @param str チェック対象文字列
	 * @return true：チェックOK、false：チェックNG
	 */
	public static boolean isNumber(String str) {

		try {

			Double.parseDouble(str);

		} catch (NumberFormatException e) {

			return false;
		}

		return true;
	}

	// charByte が SJIS ２バイト文字の第１バイトのときそのときに限り真を返す．
	private static boolean isLeadByte(int charByte) {
		return ((0x81 <= charByte) && (charByte <= 0x9F))
				|| ((0xE0 <= charByte) && (charByte <= 0xFC));
	}

	// charByte が SJIS ２バイト文字の第２バイトのときそのときに限り真を返す．
	private static boolean isTrailByte(int charByte) {
		return (0x40 <= charByte) && (charByte <= 0xFC) && (charByte != 0x7F);
	}

	/**
	 * カンマで分割した値×０．０００１を再度、カンマ区切りの文字列に編集し、設定する。
	 *
	 * @@ updateGraphValue
	 * @param inputArray money array, eg. "10000,20000,30000"
	 * @return outputArray which divided for 10k, eg. "1,2,3"
	 */
	public static String updateGraphValue(String inputArray) {
		if (StringUtil.isEmpty(inputArray)) {
			return inputArray;
		}

		String[] stringArr = inputArray.split(",");
		StringBuilder output = new StringBuilder();
		BigDecimal tenThousand = BigDecimal.valueOf(10000);
		String prefix = "";
		for (String element : stringArr) {
			output.append(prefix);
			prefix = ",";
			output.append(new BigDecimal(element).divide(
					tenThousand,
					1,
					RoundingMode.DOWN));
		}
		return output.toString();
	}

    /**
     * サニタイジングする。
     * @@
     * put
     * @note
     * @param inValue    入力：対象文字列
     * @return String    変換後文字列
     * @@
     */
    public static String replaceValue(String inValue){
        if(inValue == null){
            return inValue;
        }

        String outValue = inValue;

        outValue = outValue.replace("&", "&amp;");
        outValue = outValue.replace("\"", "&quot;");
        outValue = outValue.replace("<", "&lt;");
        outValue = outValue.replace(">", "&gt;");
        outValue = outValue.replace("'", "&#39;");

        return outValue;
    }
	
	/**
	 * Sum total of array。
	 *
	 * @@ sumData
	 * @param inputArray money array, eg. "10000,20000,30000"
	 * @return outputArray which summarized
	 */
	public static BigDecimal sumData(String inputArray) {
		if (StringUtil.isEmpty(inputArray)) {
			return BigDecimal.ZERO;
		}

		String[] stringArr = inputArray.split(",");
		BigDecimal total = BigDecimal.ZERO;
		for (String element : stringArr) {
			total = total
					.add(StringUtil.toBigDecimal(element, BigDecimal.ZERO));
		}
		return total;
	}

	/**
	 * 指定要素まで値を合算する
	 *
	 * @@ sumData
	 * @param inputArray money array, eg. "10000,20000,30000"
	 * @return outputArray which summarized
	 */
	public static BigDecimal sumData(String inputArray,int startCount, int endCount) {
		// 要素がない場合
		if (StringUtil.isEmpty(inputArray)) {
			return BigDecimal.ZERO;
		}

		// 開始／終了が逆の場合
		if (startCount > endCount){
			return BigDecimal.ZERO;
		}

		int count = 0;

		String[] stringArr = inputArray.split(",");
		BigDecimal total = BigDecimal.ZERO;

		for (String element : stringArr) {
			// 加算判定
			if( count >= startCount ){
				total = total.add(StringUtil.toBigDecimal(element, BigDecimal.ZERO));
			}

			// 終了判定
			if( count++ >= endCount ){
				break;
			}
		}

		return total;
	}

	/**
	 * 配列を指定要素で再構築する
	 *
	 * @@ sumData
	 * @param expenseResult money array, eg. "10000,20000,30000"
	 * @return outputArray which summarized
	 */
	public static String[] reduceArray(String[] oldStringArr,int maxCount) {
		if (oldStringArr == null) {
			return oldStringArr;
		}

        int maxArrayCount = maxCount;

        if (maxArrayCount >= oldStringArr.length) {
			maxArrayCount = oldStringArr.length - 1;
		}
		
		String[] newStringArr = new String[maxCount+1];
		
		for (int idx=0; idx<=maxArrayCount; idx++) {
			newStringArr[idx] = oldStringArr[idx];
		}

		return newStringArr;
	}

	/**
	 * カンマ文字列を指定要素で再構築する
	 *
	 * @@ sumData
	 * @param inputArray money array, eg. "10000,20000,30000"
	 * @return outputArray which summarized
	 */
	public static String reduceString(String inputArray,int maxCount) {
		if (inputArray == null || StringUtil.isEmpty(inputArray)) {
			return inputArray;
		}

		String[] oldStringArr = inputArray.split(",");
		StringBuilder returnString = new StringBuilder();

		for (int idx=0; idx<=maxCount; idx++) {
			if(idx != 0){
				returnString.append(",");
			}
			returnString.append(oldStringArr[idx]);
		}

		return returnString.toString();
	}

	/**
	 * Convert yen to man yen。
	 * @param value yen value
	 * @return man yen
	 */
	public static String getManYen(BigDecimal value) {
		if (value == null) {
			return null;
		} else {
			return String.valueOf(value.divide(BigDecimal.valueOf(10000)));
		}
	}

	/**
	 * Convert yen to man yen number。
	 * @param value yen value
	 * @return manyen number
	 */
	public static BigDecimal getManYenN(BigDecimal value) {
		if (value == null) {
			return null;
		} else {
			return value.divide(BigDecimal.valueOf(10000));
		}
	}
}