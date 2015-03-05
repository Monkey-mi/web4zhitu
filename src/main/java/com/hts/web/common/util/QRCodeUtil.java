package com.hts.web.common.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public class QRCodeUtil {

	/**
	 * 生成二维码
	 * @throws IOException
	 * @throws WriterException
	 */
	public static void createQRCode(String params,int width, int height,OutputStream out) throws WriterException, IOException {
        String format = "jpg";  
        Map<EncodeHintType, Object> hints= new HashMap<EncodeHintType, Object>();  
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");  
        BitMatrix bitMatrix = new MultiFormatWriter().encode(params, BarcodeFormat.QR_CODE, width, height,hints);  
        MatrixToImageWriter.writeToStream(bitMatrix, format, out);
	}
}
