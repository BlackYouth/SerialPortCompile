
package android_serialport_api;

import java.io.File;
import java.io.IOException;

import android.util.Log;

public class SerialPort {

	private static final String TAG = "SerialPort";

	private int mFd;

	public SerialPort(File device, int baudrate, int flags) throws SecurityException, IOException {

		/* Check access permission */
		if (!device.canRead() || !device.canWrite()) {
			try {
				/* Missing read/write permission, trying to chmod the file */
				Process su;
				su = Runtime.getRuntime().exec("/system/bin/su");
				String cmd = "chmod 666 " + device.getAbsolutePath() + "\n"
						+ "exit\n";
				su.getOutputStream().write(cmd.getBytes());
				if ((su.waitFor() != 0) || !device.canRead()
						|| !device.canWrite()) {
					throw new SecurityException();
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new SecurityException();
			}
		}

		mFd = open(device.getAbsolutePath(), baudrate, flags);
		if (mFd < 0) {
			Log.d(TAG, "native open returns null");
			throw new IOException();
		}
	}

	public int writeData(byte[] bytes){
		return write(mFd,bytes);
	}

	/**
	 * 读取应答数据
	 * @param bytes 接受数据数组
	 * @param outTime 读取超时时间（毫秒） 0:阻塞读取，大于0：未阻塞，设定超时时间读取
	 * @return 大于0：读取数据成功，等于0：读取超时，小于0：读取失败
	 */
	public int readData(byte[] bytes,long outTime){
		return read(mFd,bytes,outTime);
	}

	public int close(){
		return close(mFd);
	}

	// JNI
	private native int open(String path, int baudrate, int flags);
	private native int write(int mFd,byte[] bytes);
	private native int read(int mFd,byte[] bytes,long outTime);
	private native int close(int mFd);
	static {
		System.loadLibrary("serialport");
	}
}
