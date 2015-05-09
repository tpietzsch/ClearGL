package cleargl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL3;
import javax.media.opengl.GL4;
import javax.media.opengl.GLException;

import org.apache.commons.io.IOUtils;

public class GLShader implements GLInterface, GLCloseable
{
	private final GL mGL;
	private final int mShaderId;
	private final GLShaderType mShaderType;
	private final String mShaderSource;

	public GLShader(GL pGL,
									Class<?> pRootClass,
									String pRessourceName,
									GLShaderType pShaderType) throws IOException
	{
		super();
		mGL = pGL;
		final InputStream lResourceAsStream = pRootClass.getResourceAsStream(pRessourceName);
		mShaderSource = IOUtils.toString(lResourceAsStream, "UTF-8");
		mShaderType = pShaderType;

		final HashMap<GLShaderType, Integer> glShaderTypeMapping = new HashMap<GLShaderType, Integer>();
		glShaderTypeMapping.put(GLShaderType.VertexShader,
														GL2.GL_VERTEX_SHADER);
		glShaderTypeMapping.put(GLShaderType.GeometryShader,
														GL3.GL_GEOMETRY_SHADER);
		glShaderTypeMapping.put(GLShaderType.TesselationControlShader,
														GL4.GL_TESS_CONTROL_SHADER);
		glShaderTypeMapping.put(GLShaderType.TesselationEvaluationShader,
														GL4.GL_TESS_EVALUATION_SHADER);
		glShaderTypeMapping.put(GLShaderType.FragmentShader,
														GL2.GL_FRAGMENT_SHADER);

		final int lShaderTypeInt = glShaderTypeMapping.get(pShaderType);

		mShaderId = pGL.getGL3().glCreateShader(lShaderTypeInt);
		mGL.getGL3().glShaderSource(mShaderId, 1, new String[]
		{ mShaderSource }, null);
		mGL.getGL3().glCompileShader(mShaderId);

	}

	public GLShader(GL pGL,
									String pShaderSourceAsString,
									GLShaderType pShaderType) throws IOException
	{
		super();
		mGL = pGL;
		mShaderSource = pShaderSourceAsString;
		mShaderType = pShaderType;

		final HashMap<GLShaderType, Integer> glShaderTypeMapping = new HashMap<GLShaderType, Integer>();
		glShaderTypeMapping.put(GLShaderType.VertexShader,
														GL4.GL_VERTEX_SHADER);
		glShaderTypeMapping.put(GLShaderType.GeometryShader,
														GL4.GL_GEOMETRY_SHADER);
		glShaderTypeMapping.put(GLShaderType.TesselationControlShader,
														GL4.GL_TESS_CONTROL_SHADER);
		glShaderTypeMapping.put(GLShaderType.TesselationEvaluationShader,
														GL4.GL_TESS_EVALUATION_SHADER);
		glShaderTypeMapping.put(GLShaderType.FragmentShader,
														GL4.GL_FRAGMENT_SHADER);

		final int lShaderTypeInt = glShaderTypeMapping.get(pShaderType);

		mShaderId = pGL.getGL3().glCreateShader(lShaderTypeInt);
		mGL.getGL3().glShaderSource(mShaderId, 1, new String[]
		{ mShaderSource }, null);
		mGL.getGL3().glCompileShader(mShaderId);

	}

	@Override
	public void close() throws GLException
	{
		mGL.getGL3().glDeleteShader(mShaderId);
	}

	public String getShaderInfoLog()
	{
		final int logLen = getShaderParameter(GL4.GL_INFO_LOG_LENGTH);
		if (logLen <= 0)
			return "";

		final int[] lLength = new int[1];
		final byte[] lBytes = new byte[logLen + 1];
		mGL.getGL3().glGetShaderInfoLog(mShaderId,
																		logLen,
																		lLength,
																		0,
																		lBytes,
																		0);
		final String logMessage = new String(lBytes);
		return logMessage;
	}

	public int getShaderParameter(int pParamName)
	{
		final int lParameter[] = new int[1];
		mGL.getGL3().glGetShaderiv(mShaderId, pParamName, lParameter, 0);
		return lParameter[0];
	}

	@Override
	public int getId()
	{
		return mShaderId;
	}

	@Override
	public GL getGL()
	{
		return mGL;
	}

	@Override
	public String toString()
	{
		return "GLShader [mGL=" + mGL
						+ ", mShaderId="
						+ mShaderId
						+ ", mShaderType="
						+ mShaderType
						+ ", mShaderSource="
						+ mShaderSource
						+ "]";
	}

}
