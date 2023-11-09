/*********************************************************************
 * 文字列の編集を行います。
 * @@
 * StringUtil.java
 * WebAPI　共通部品
 *
 * クラス一覧
 *     No  クラス名                概要
 *     01  StringUtil　　　　　　　文字列の編集を行います。
 *
 * 履歴
  * 履歴
 *     No       日付        Ver             更新者              内容
 *     00001    2023/11/09　V0001L00001     Yuki Takahashi     Initial
 *
 * Copyright(C) Metaease 2023
 *
 * @author  Y.Takahashi
 *********************************************************************/
package jp.co.metaease.com;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class StringUtil {

	/**
	 * 半角カタカナ文字データテーブルです。
	 */
	private static final String HANKAKU_KATAKANA[] = {
		"ｧ", "ｱ", "ｨ", "ｲ", "ｩ", "ｳ", "ｪ", "ｴ", "ｫ", "ｵ", "ｶ", "ｶﾞ", "ｷ", "ｷﾞ",
		"ｸ", "ｸﾞ", "ｹ", "ｹﾞ", "ｺ", "ｺﾞ", "ｻ", "ｻﾞ", "ｼ", "ｼﾞ", "ｽ", "ｽﾞ", "ｾ",
		"ｾﾞ", "ｿ", "ｿﾞ", "ﾀ", "ﾀﾞ", "ﾁ", "ﾁﾞ", "ｯ", "ﾂ", "ﾂﾞ", "ﾃ", "ﾃﾞ", "ﾄ",
		"ﾄﾞ", "ﾅ", "ﾆ", "ﾇ", "ﾈ", "ﾉ", "ﾊ", "ﾊﾞ", "ﾊﾟ", "ﾋ", "ﾋﾞ", "ﾋﾟ", "ﾌ",
		"ﾌﾞ", "ﾌﾟ", "ﾍ", "ﾍﾞ", "ﾍﾟ", "ﾎ", "ﾎﾞ", "ﾎﾟ", "ﾏ", "ﾐ", ".", "ﾑ", "ﾒ",
		"ﾓ", "ｬ", "ﾔ", "ｭ", "ﾕ", "ｮ", "ﾖ", "ﾗ", "ﾘ", "ﾙ", "ﾚ", "ﾛ", "ﾜ", "ﾜ",
		"ｲ", "ｴ", "ｦ", "ﾝ", "ｳﾞ", "ｶ", "ｹ"
	};

	/**
	 * 全角カタカナ文字データテーブルです。
	 */
	private static final String ZENKAKU_KATAKANA[] = {
		"ァ", "ア", "ィ", "イ", "ゥ", "ウ", "ェ", "エ", "ォ", "オ", "カ", "ガ", "キ", "ギ",
		"ク", "グ", "ケ", "ゲ", "コ", "ゴ", "サ", "ザ", "シ", "ジ", "ス", "ズ", "セ", "ゼ",
		"ソ", "ゾ", "タ", "ダ", "チ", "ヂ", "ッ", "ツ", "ヅ", "テ", "デ", "ト", "ド", "ナ",
		"ニ", "ヌ", "ネ", "ノ", "ハ", "バ", "パ", "ヒ", "ビ", "ピ", "フ", "ブ", "プ", "ヘ",
		"ベ", "ペ", "ホ", "ボ", "ポ", "マ", "ミ", "．", "ム", "メ", "モ", "ャ", "ヤ", "ュ",
		"ユ", "ョ", "ヨ", "ラ", "リ", "ル", "レ", "ロ", "ワ", "ヮ", "ヰ", "ヱ", "ヲ", "ン",
		"ヴ", "ヵ", "ヶ"
	};

	/**
	 * コンストラクターです。
	 * @@
	 * StringUtil
	 * @note
	 * @@
	 */
	private StringUtil() {
	}

	/**
	 * 文字列が存在しないか判定します。
	 * @@
	 * isEmpty
	 * @note
	 * @param str 文字列
	 * @return 判定結果（true：文字列存在無し false：文字列存在有り）
	 * @@
	 */
	public static boolean isEmpty(String str) {
		// nullの場合はtureです。
		if (str == null) {
			return true;
		}
		// 空文字の場合はtrueです。
		return str.equals("");
	}

	/**
	 *
	 * 文字数まで左右どちらかに側に文字を埋めます。変換できない場合はそのままの値を返します。
	 * @@
	 * paddingString
	 * @note
	 * @param str 文字列
	 * @param length 文字数
	 * @param sPaddingStr 埋める文字
	 * @param leftFlg （true：左側に文字を埋める false：右側に文字を埋める）
	 * @return 結果文字列
	 * @@
	 */
	public static String paddingString(
			String str,
			int length,
			String sPaddingStr,
			boolean leftFlg) {

		// 文字列がnullはそのままの値を返す。
		if (str == null) {
			return str;
		}

		// 埋める文字がnull、空文字はそのままの値を返す。
		if (isEmpty(sPaddingStr)) {
			return str;
		}

		str = str.trim();

		StringBuffer sb = new StringBuffer();

		// 指定された文字列長まで埋め文字を加えます。
		if (str.length() < length) {
			for (int loop = str.length(); loop < length; loop++) {
				sb.append(sPaddingStr);
			}
		} else {
			// 文字列長が文字数よりも長い場合はそのままの値を返す。
			return str;
		}

		// 返却する文字列を作成します。
		String result;
		if (leftFlg) {
			// 左側に文字を埋めます。
			result = sb.toString() + str;
		} else {
			// 右側に文字を埋めます。
			result = str + sb.toString();
		}

		// 文字数をオーバーした場合はそのままの値を返す。
		if (result.length() > length) {
			return str;
		}

		// 結果を返却します。
		return result;
	}

	/**
	 *
	 * 全角文字を半角に変換します。※半角文字はそのままとします。
	 * @@
	 * zen2han
	 * @note
	 * @param str 全角文字列
	 * @exception IllegalArgumentException 引数の不備
	 * @return 変換後文字列（全角→半角）
	 * @@
	 */
	public static String zen2han(String str) throws IllegalArgumentException {

		// 文字列がnull、空文字はそのままの値を返す。
		if (isEmpty(str)) {
			return str;
		}

		// 引数の文字列を文字に分解します。
		char chars[] = str.toCharArray();
		int length = chars.length;

		// 変換後の文字列を生成するための文字列バッファです。
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < length; i++) {

			// 全角文字かどうかチェックします。
			String value = String.valueOf(chars[i]);
			if (value.getBytes().length >= 2) {

				// 全角カタカナの場合
				if ('\u30a1' <= chars[i] && chars[i] <= '\u30f6') {

					// 全角カタカナテーブル分繰り返し
					for (int l = 0; l < ZENKAKU_KATAKANA.length; l++) {

						// 全角カタカナテーブルの値と一致した場合
						if (value.equals(ZENKAKU_KATAKANA[l])) {

							// 見つかった場合は対応する半角カタカナを選びます。
							sb.append(HANKAKU_KATAKANA[l]);
							break;
						}
					}

					// 全角数字の場合
				} else if ('\uff10' <= chars[i] && chars[i] <= '\uff19') {

					// 一律に 0xfee0 だけ前にシフトします。
					sb.append((char) (chars[i] - 0xfee0));

					// 全角英字大文字の場合
				} else if ('\uff21' <= chars[i] && chars[i] <= '\uFF3a') {

					// 一律に 0xfee0 だけ前にシフトします。
					sb.append((char) (chars[i] - 0xfee0));

					// 全角英字小文字の場合
				} else if ('\uff41' <= chars[i] && chars[i] <= '\uff5a') {

					// 一律に 0xfee0 だけ前にシフトします。
					sb.append((char) (chars[i] - 0xfee0));

				} else {

					switch (chars[i]) {

					// 半角カナ記号
					case '\u3002': // "。"
						sb.append('｡');
						break;
					case '\u300c': // "「"
						sb.append('｢');
						break;
					case '\u300d': // "」"
						sb.append('｣');
						break;
					case '\u3001': // "、"
						sb.append('､');
						break;
					case '\u30fb': // "・"
						sb.append('･');
						break;
					case '\u30fc': // "ー"
						sb.append('ｰ');
						break;
					case '\u309b': // "゛"
						sb.append('ﾞ');
						break;
					case '\u309c': // "゜"
						sb.append('ﾟ');
						break;

					// 半角英数記号
					case '\u3000': // " "
						sb.append('\u0020');
						break;
					case '\uff01': // ！
						sb.append('\u0021');
						break;
					case '\u201d': // ”
						sb.append('\u0022');
						break;
					case '\uff03': // ＃
						sb.append('\u0023');
						break;
					case '\uff04': // ＄
						sb.append('\u0024');
						break;
					case '\uff05': // ％
						sb.append('\u0025');
						break;
					case '\uff06': // ＆
						sb.append('\u0026');
						break;
					case '\u2019': // ’
						sb.append('\'');
						break;
					case '\uff08': // （
						sb.append('\u0028');
						break;
					case '\uff09': // ）
						sb.append('\u0029');
						break;
					case '\uff0a': // ＊
						sb.append('\u002a');
						break;
					case '\uff0b': // ＋
						sb.append('\u002b');
						break;
					case '\uff0c': // ，
						sb.append('\u002c');
						break;
					case '\uff0d': // －
						//						case '\u2212': // －
						sb.append('\u002d');
						break;
					case '\uff0e': // ．
						sb.append('\u002e');
						break;
					case '\uff0f': // ／
						sb.append('\u002f');
						break;
					case '\uff1a': // ：
						sb.append('\u003a');
						break;
					case '\uff1b': // ；
						sb.append('\u003b');
						break;
					case '\uff1c': // ＜
						sb.append('\u003c');
						break;
					case '\uff1d': // ＝
						sb.append('\u003d');
						break;
					case '\uff1e': // ＞
						sb.append('\u003e');
						break;
					case '\uff1f': // ？
						sb.append('\u003f');
						break;
					case '\uff20': // ＠
						sb.append('\u0040');
						break;
					case '\uff3b': // ［
						sb.append('\u005b');
						break;
					case '\uffe5': // ￥
						sb.append('\\');
						break;
					case '\uff3d': // ］
						sb.append('\u005d');
						break;
					case '\uff3e': // ＾
						sb.append('\u005e');
						break;
					case '\uff3f': // ＿
						sb.append('\u005f');
						break;
					case '\u2018': // ‘
						sb.append('\u0060');
						break;
					case '\uff5b': // ｛
						sb.append('\u007b');
						break;
					case '\uff5c': // ｜
						sb.append('\u007c');
						break;
					case '\uff5d': // ｝
						sb.append('\u007d');
						break;
					// case '\u301c': // ～
					case '\uff5e': // ～
						sb.append('\u007e');
						// sb.append('\u203e');
						break;
					default:
						break;
					}
				}

			} else {

				// 半角文字はそのまま
				sb.append(str.substring(i, i + 1));
			}
		}

		// 変換後の文字列を返します。
		return sb.toString();
	}

	/**
	 *
	 * 半角英・数・カタカナ・記号を全角に変換します。※全角文字はそのままとします。
	 * @@
	 * katakana
	 * @note
	 * @param str 文字列
	 * @exception IllegalArgumentException 引数の不備
	 * @return 変換後文字列（半角→全角）
	 * @@
	 */
	public static String katakana(String str) throws IllegalArgumentException {

		// 文字列がnull、空文字はそのままの値を返す。
		if (isEmpty(str)) {
			return str;
		}

		// 引数の文字列を文字に分解します。
		char chars[] = str.toCharArray();
		int length = chars.length;

		// 変換後の文字列を生成するための文字列バッファです。
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < length; i++) {

			// 半角文字かどうかチェックします。
			String value = String.valueOf(chars[i]);

			int valueLen = 0;
			try{
				valueLen = value.getBytes("MS932").length;
			}catch(Exception ex){
				valueLen = 1;
			}

			if (valueLen == 1) {
				// 半角カタカナの場合（'\uff70'「ｰ」でない場合も含む）
				if ('\uff66' <= chars[i]
						&& chars[i] <= '\uff9d'
						&& chars[i] != '\uff70') {

					// 最終の文字でなく、次の文字が濁点、半濁点なら、 濁点、半濁点ありの半角カタカナ
					if (i != length - 1
							&& (chars[i + 1] == 'ﾞ' || chars[i + 1] == 'ﾟ')) {

						// 濁点、半濁点付きでチェックします。
						value = value + String.valueOf(chars[i + 1]);

						// 半角カタカナテーブル分繰り返し
						for (int l = 0; l < HANKAKU_KATAKANA.length; l++) {
							// 半角カタカナテーブルの値と一致した場合
							if (value.equals(HANKAKU_KATAKANA[l])) {

								// 見つかった場合は対応する全角カタカナを選びます。
								sb.append(ZENKAKU_KATAKANA[l]);

								// 2文字を1文字に変換したのでひとつずらします。
								i++;
								break;
							}
						}

						// 濁点、半濁点ありの全角カタカナに変換できなかった場合

						// チェック文字を入れ替えます。
						value = String.valueOf(chars[i]);

						// 半角カタカナテーブル分繰り返し
						for (int l = 0; l < HANKAKU_KATAKANA.length; l++) {

							// 全角カタカナテーブルの値と一致した場合
							if (value.equals(HANKAKU_KATAKANA[l])) {

								// 見つかった場合は対応する半角カタカナを選びます。
								sb.append(ZENKAKU_KATAKANA[l]);
								break;
							}
						}

						// 濁点、半濁点なしの半角カタカナ
					} else {

						// 半角カタカナテーブル分繰り返し
						for (int l = 0; l < HANKAKU_KATAKANA.length; l++) {

							// 全角カタカナテーブルの値と一致した場合
							if (value.equals(HANKAKU_KATAKANA[l])) {

								// 見つかった場合は対応する半角カタカナを選びます。
								sb.append(ZENKAKU_KATAKANA[l]);
								break;
							}
						}
					}

					// 半角数字の場合
				} else if ('\u0030' <= chars[i] && chars[i] <= '\u0039') {

					// 一律に 0xfee0 だけ後にシフトします。
					sb.append((char) (chars[i] + 0xfee0));

					// 半角英字大文字の場合
				} else if ('\u0041' <= chars[i] && chars[i] <= '\u005a') {

					// 一律に 0xfee0 だけ後にシフトします。
					sb.append((char) (chars[i] + 0xfee0));

					// 半角英字小文字の場合
				} else if ('\u0061' <= chars[i] && chars[i] <= '\u007a') {

					// 一律に 0xfee0 だけ後にシフトします。
					sb.append((char) (chars[i] + 0xfee0));

				} else {

					switch (chars[i]) {

					// 半角カナ記号
					case '｡': // "。"
						sb.append('\u3002');
						break;
					case '｢': // "「"
						sb.append('\u300c');
						break;
					case '｣': // "」"
						sb.append('\u300d');
						break;
					case '､': // "、"
						sb.append('\u3001');
						break;
					case '･': // "・"
						sb.append('\u30fb');
						break;
					case 'ｰ': // "ー"
						sb.append('\u30fc');
						break;
					case 'ﾞ': // "゛"
						sb.append('\u309b');
						break;
					case 'ﾟ': // "゜"
						sb.append('\u309c');
						break;

					// 半角英数記号
					case '\u0020': // " "
						sb.append('\u3000');
						break;
					case '\u0021': // ！
						sb.append('\uff01');
						break;
					case '\u0022': // ”
						sb.append('\u201d');
						break;
					case '\u0023': // ＃
						sb.append('\uff03');
						break;
					case '\u0024': // ＄
						sb.append('\uff04');
						break;
					case '\u0025': // ％
						sb.append('\uff05');
						break;
					case '\u0026': // ＆
						sb.append('\uff06');
						break;
					case '\'': // ’
						sb.append('\u2019');
						break;
					case '\u0028': // （
						sb.append('\uff08');
						break;
					case '\u0029': // ）
						sb.append('\uff09');
						break;
					case '\u002a': // ＊
						sb.append('\uff0a');
						break;
					case '\u002b': // ＋
						sb.append('\uff0b');
						break;
					case '\u002c': // ，
						sb.append('\uff0c');
						break;
					case '\u002d': // －
						// sb.append('\u2212');
						sb.append('\uff0d');
						break;
					case '\u002e': // ．
						sb.append('\uff0e');
						break;
					case '\u002f': // ／
						sb.append('\uff0f');
						break;
					case '\u003a': // ：
						sb.append('\uff1a');
						break;
					case '\u003b': // ；
						sb.append('\uff1b');
						break;
					case '\u003c': // ＜
						sb.append('\uff1c');
						break;
					case '\u003d': // ＝
						sb.append('\uff1d');
						break;
					case '\u003e': // ＞
						sb.append('\uff1e');
						break;
					case '\u003f': // ？
						sb.append('\uff1f');
						break;
					case '\u0040': // ＠
						sb.append('\uff20');
						break;
					case '\u005b': // ［
						sb.append('\uff3b');
						break;
					case '\\': // ￥
						sb.append('\uffe5');
						break;
					case '\u005d': // ］
						sb.append('\uff3d');
						break;
					case '\u005e': // ＾
						sb.append('\uff3e');
						break;
					case '\u005f': // ＿
						sb.append('\uff3f');
						break;
					case '\u0060': // ‘
						sb.append('\u2018');
						break;
					case '\u007b': // ｛
						sb.append('\uff5b');
						break;
					case '\u007c': // ｜
						sb.append('\uff5c');
						break;
					case '\u007d': // ｝
						sb.append('\uff5d');
						break;
					case '\u007e': // ～
						sb.append('\uff5e');
						break;
					// case '\u203e': // ～
					// 	sb.append('\u301c');
					// 	break;
					default:
						break;
					}
				}

			} else {

				// 全角文字はそのまま
				sb.append(str.substring(i, i + 1));
			}
		}

		// 変換後の文字列を返します。
		return sb.toString();
	}

	/**
	 *
	 * 文字列の後方半角空白を削除します。 @@ removeRightSpace
	 * @note
	 * @param str
	 *            文字列
	 * @return 変換後文字列 @@
	 */
	public static String removeRightSpace(String str) {

		// 引数がnull又は空白の場合はそのままの値を返します。
		if (isEmpty(str)) {
			return str;
		}

		// 文字列の後ろから空白かどうかをチェック
		int i = 0;
		char chWk = ' ';
		// 全角・半角両方の空白を削除します。
		for (i = str.length() - 1; i >= 0; i--) {
			chWk = str.charAt(i);
			if (chWk > '\u0020') {
				break;
			}
		}
		// 後ろの空白を削除した文字列を返します。
		return str.substring(0, i + 1);
	}

	/**
	 *
	 * 文字列の前方半角空白を削除します。 @@ removeRightSpace
	 * @note
	 * @param str
	 *            文字列
	 * @return 変換後文字列 @@
	 */
	public static String removeLeftSpace(String str) {

		// 引数がnull又は空白の場合はそのままの値を返します。
		if (isEmpty(str)) {
			return str;
		}

		// 文字列の後ろから空白かどうかをチェック
		int i = 0;
		char chWk = ' ';
		// 全角・半角両方の空白を削除します。
		for (i = 0; i < str.length(); i++) {
			chWk = str.charAt(i);
			if (chWk > '\u0020') {
				break;
			}
		}
		// 後ろの空白を削除した文字列を返します。
		return str.substring(i);
	}

	/**
	 *
	 * 指定された「delim」単位で文字列を分割します。
	 * @@
	 * stringDivisionOfDelim
	 * @note
	 * @param str 分割対象文字列
	 * @param cDelim トークン
	 * @return 分割結果文字列
	 * @@
	 */
	@SuppressWarnings("unchecked")
	public static String[] stringDivisionOfDelim(String str, char cDelim) {
		@SuppressWarnings("rawtypes")
		ArrayList strList = new ArrayList();

		// 開始位置
		int iIdxStart = 0;

		// 開始位置
		int iIdxEnd = 0;

		// 現在の開始位置以降に「delim」が存在する場合は繰り返し実行します。同時に終了位置を設定します。
		while ((iIdxEnd = str.indexOf(cDelim, iIdxStart)) != -1) {

			// 開始位置と終了位置の間の文字列をリストに追加します。
			strList.add(str.substring(iIdxStart, iIdxEnd));

			// 開始位置を終了位置の一つ後にします。
			iIdxStart = iIdxEnd + 1;
		}

		// 現在の開始位置が文字列数より小さい場合
		if (str.length() > iIdxStart) {
			// 最後の文字列を追加します。
			strList.add(str.substring(iIdxStart));
		}

		// 文字列のリストを返却します。
		return (String[]) strList.toArray(new String[strList.size()]);
	}

	/**
	 *
	 * 指定された「delim」が連続で存在した場合それ以降の文字列を取得します。
	 * @@
	 * stringDivision
	 * @note
	 * @param str 分割対象文字列
	 * @param cDelim トークン
	 * @return 分割結果文字列
	 * @@
	 */
	public static String stringDivision(String str, char cDelim) {

		// 開始位置のdelimを設定します。
		int iIdxStart = str.indexOf(cDelim);

		// 連続出現フラグ
		boolean flg = false;

		// 現在の開始位置以降に「delim」が存在する場合は繰り返し実行します。同時に終了位置を設定します。
		while (iIdxStart != -1) {
			// 開始位置のdelimを設定します。
			iIdxStart = str.indexOf(cDelim, iIdxStart);

			// 文字数を超えた場合は空文字を返却
			if (iIdxStart >= str.length() - 1) {
				return "";
			}

			// チェックした次の文字の次の文字も「delim」と同じかどうかチェック
			if (str.charAt(iIdxStart + 1) == cDelim) {
				flg = true;
				iIdxStart = iIdxStart + 1;
				if (iIdxStart >= str.length() - 1) {
					return "";
				}
			} else {
				// 「delim」が連続出現してる場合
				if (flg) {
					// 開始位置以降の文字列を返却します。
					return str.substring(iIdxStart + 1, str.length());
				} else {
					// 開始位置を＋１します。
					iIdxStart = str.indexOf(cDelim, iIdxStart + 1);
				}
			}
		}

		// 文字列のリストを返却します。
		return "";

	}

	/**
	 *
	 * 文字列中の半角スペースを削除します。
	 * @@
	 * removeSpace
	 * @note
	 * @param str 文字列
	 * @return 半角スペースを削除した文字列
	 * @@
	 */
	public static String removeHalfSpace(String str) {

		// 引数がnull又は空白の場合はそのままの値を返します。
		if (str == null) {
			return str;
		}

		// 文字列中の" "を""に置き換えます。
		return str.replaceAll(" ", "");
	}

	/**
	 *
	 * 文字列中の全角スペースを削除します。
	 * @@
	 * removeSpace
	 * @note
	 * @param str 文字列
	 * @return 全角スペースを削除した文字列
	 * @@
	 */
	public static String removeFullSpace(String str) {

		// 引数がnull又は空白の場合はそのままの値を返します。
		if (str == null) {
			return str;
		}

		// 文字列中の"　"を""に置き換えます。
		return str.replaceAll("　", "");
	}

	/**
	 *
	 * 文字列中の半角スペースを削除します。
	 * @@
	 * removeSpace
	 * @note
	 * @param str 文字列
	 * @return 半角スペースを削除した文字列
	 * @@
	 */
	public static String removeAllSpace(String str) {

		// 引数がnull又は空白の場合はそのままの値を返します。
		if (str == null) {
			return str;
		}

		// 文字列中の" "と"　"を""に置き換えます。
		return removeFullSpace(removeHalfSpace(str));
	}

	/**
	 *
	 * 文字列より、数字のみを抽出します。
	 * @@
	 * extractionNumber
	 * @note
	 * @param str 文字列
	 * @return 数字のみを抽出した文字列
	 * @@
	 */
	public static String extractionNumber(String str) {

		// 引数がnull又は空白の場合はそのままの値を返します。
		if (isEmpty(str)) {
			return str;
		}

		byte bytes[] = null;
		try {
			bytes = str.getBytes("MS932");
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException("文字コードがサポートされていません。", ex);
		}

		ByteArrayOutputStream os = new ByteArrayOutputStream();

		for (int i = 0; i < bytes.length; i++) {
			byte b = bytes[i];
			if (b >= (byte) 0x81
					&& b <= (byte) 0x9f
					|| b >= (byte) 0xe0
					&& b <= (byte) 0xfc) {
				i++;
				continue;
			} else if (b < 0x30 || b > 0x39) {
				continue;
			}
			os.write(b);
		}
		byte raw[] = os.toByteArray();
		String changed = null;
		try {
			changed = new String(raw, "MS932");
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException("文字コードがサポートされていません。", ex);
		}
		return changed;
	}

	/**
	 * 空文字チェック（TABLE用）
	 * 空文字又はNull文字を'―'(全角ハイフン)に置き換えます。
	 * @@
	 * strCheck
	 * @note
	 * @param str 入力：文字列
	 * @return 文字列
	 * @@
	 */
	public static String changeToHyphen(String str) {
		// 引数strがNullでない場合
		if (!StringUtil.isEmpty(str)) {

			// 半角スペース場合、全角ハイフンを返却
			if (str.equals(" ")) {
				return "―";
				// 全角スペースの場合、全角ハイフンを返却
			} else if (str.equals("　")) {
				return "―";

				// 全角スペース又は半角スペースでない場合、strを返却
			} else {
				return str;
			}

			// 引数strがNullの場合、全角スペースを返却
		} else {
			return "―";
		}
	}

	/**
	 * Returns the string representation of the <code>Object</code> argument.
	 *
	 * @param   obj   an <code>Object</code>.
	 * @return  if the argument is <code>null</code>, then
	 *          <code>null</code>; otherwise, the value of
	 *          <code>obj.toString()</code> is returned.
	 * @see     java.lang.Object#toString()
	 */
	public static String valueOf(Object obj) {
		if (obj == null) {
			return null;
		}
		return obj.toString();
	}

	/**
	 * Returns the string representation of the <code>Object</code> argument.
	 *
	 * @param   obj   an <code>Object</code>.
	 * @param   defaultValue   an <code>String</code>.
	 * @return  if the argument is <code>null</code>, then
	 *          <code>defaultValue</code>; otherwise, the value of
	 *          <code>obj.toString()</code> is returned.
	 * @see     java.lang.Object#toString()
	 */
	public static String valueOf(Object obj, String defaultValue) {
		if (obj == null) {
			return defaultValue;
		}
		return obj.toString();
	}

	/**
	 * Returns the string representation of the <code>Object</code> argument.
	 *
	 * @param   obj   an <code>Object</code>.
	 * @return  if the argument is <code>null</code>, then
	 *          <code>null</code>; otherwise, the value of
	 *          <code>obj.toString()</code> is returned.
	 * @see     java.lang.Object#toString()
	 */
	public static BigDecimal toBigDecimal(String obj) {
		return toBigDecimal(obj, null);
	}

	/**
	 *
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static BigDecimal toBigDecimal(String obj, BigDecimal defaultValue) {
		if (StringUtil.isEmpty(obj)) {
			return defaultValue;
		}
		return new BigDecimal(obj);
	}

    /**
	 * 改行文字のサニタイジング
	 * 改行文字が表示されるようにサニタイズします。
	 * @@
	 * sanitizeLineBreak
	 * @note
	 * @param str 入力：変換前文字列
	 * @return 変換後文字列
	 * @@
	 */
	public static String sanitizeLineBreak(String str) {
		// 引数strがNullでない場合
		String outValue = null;
		if (!StringUtil.isEmpty(str)) {
			
			outValue = str;
			
			outValue = outValue.replaceAll("\r\n", "<br>");
			outValue = outValue.replaceAll("\n\r", "<br>");
			outValue = outValue.replaceAll("\n", "<br>");
			outValue = outValue.replaceAll("\r", "<br>");
		}
		return outValue;
	}
}
