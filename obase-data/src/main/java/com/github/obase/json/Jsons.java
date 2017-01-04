package com.github.obase.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.github.obase.WrappedException;
import com.github.obase.kit.ArrayKit;

public final class Jsons {
	private Jsons() {
	}

	private static final ObjectMapper OM = new ObjectMapper();
	private static final TypeFactory TF = OM.getTypeFactory();

	static {
		OM.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		OM.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
		OM.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
		OM.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);
		OM.setSerializationInclusion(Include.NON_NULL); // ignore null
	}

	public static final JavaType Object = TF.constructParametricType(HashMap.class, String.class, Object.class);

	public static final JavaType ObjectList = TF.constructParametricType(List.class, Object);

	public static ObjectMapper getObjectMapper() {
		return OM;
	}

	public static final Map<String, Object> readObject(String json) {
		try {
			return OM.readValue(json, Object);
		} catch (IOException e) {
			throw new WrappedException(e);
		}
	}

	public static final List<Map<String, Object>> readObjectList(String json) {
		try {
			return OM.readValue(json, ObjectList);
		} catch (IOException e) {
			throw new WrappedException(e);
		}
	}

	public static final <T> T readValue(String json, Class<T> type) {
		try {
			return OM.readValue(json, type);
		} catch (IOException e) {
			throw new WrappedException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static final <T> T readGeneric(String json, Class<?> parametrized, Class<?>... parameterClasses) {
		try {
			if (ArrayKit.isNotEmpty(parameterClasses)) {
				int last = parameterClasses.length - 1;
				JavaType[] types = new JavaType[] { TF.constructType(parameterClasses[last]) };
				for (int i = last - 1; i >= 0; i--) {
					types[0] = TF.constructSimpleType(parameterClasses[i], types);
				}
				return OM.readValue(json, TF.constructSimpleType(parametrized, types));
			} else {
				return (T) OM.readValue(json, parametrized);
			}
		} catch (IOException e) {
			throw new WrappedException(e);
		}
	}

	public static final Map<String, Object> readObject(byte[] json) {
		try {
			return OM.readValue(json, Object);
		} catch (IOException e) {
			throw new WrappedException(e);
		}
	}

	public static final List<Map<String, Object>> readObjectList(byte[] json) {
		try {
			return OM.readValue(json, ObjectList);
		} catch (IOException e) {
			throw new WrappedException(e);
		}
	}

	public static final <T> T readValue(byte[] json, Class<T> type) {
		try {
			return OM.readValue(json, type);
		} catch (IOException e) {
			throw new WrappedException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static final <T> T readGeneric(byte[] json, Class<?> parametrized, Class<?>... parameterClasses) {
		try {
			if (ArrayKit.isNotEmpty(parameterClasses)) {
				int last = parameterClasses.length - 1;
				JavaType[] types = new JavaType[] { TF.constructType(parameterClasses[last]) };
				for (int i = last - 1; i >= 0; i--) {
					types[0] = TF.constructSimpleType(parameterClasses[i], types);
				}
				return OM.readValue(json, TF.constructSimpleType(parametrized, types));
			} else {
				return (T) OM.readValue(json, parametrized);
			}
		} catch (IOException e) {
			throw new WrappedException(e);
		}
	}

	public static final Map<String, Object> readObject(Reader in) {
		try {
			return OM.readValue(in, Object);
		} catch (IOException e) {
			throw new WrappedException(e);
		}
	}

	public static final List<Map<String, Object>> readObjectList(Reader in) {
		try {
			return OM.readValue(in, ObjectList);
		} catch (IOException e) {
			throw new WrappedException(e);
		}
	}

	public static final <T> T readValue(Reader in, Class<T> type) {
		try {
			return OM.readValue(in, type);
		} catch (IOException e) {
			throw new WrappedException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static final <T> T readGeneric(Reader in, Class<?> parametrized, Class<?>... parameterClasses) {
		try {
			if (ArrayKit.isNotEmpty(parameterClasses)) {
				int last = parameterClasses.length - 1;
				JavaType[] types = new JavaType[] { TF.constructType(parameterClasses[last]) };
				for (int i = last - 1; i >= 0; i--) {
					types[0] = TF.constructSimpleType(parameterClasses[i], types);
				}
				return OM.readValue(in, TF.constructSimpleType(parametrized, types));
			} else {
				return (T) OM.readValue(in, parametrized);
			}
		} catch (IOException e) {
			throw new WrappedException(e);
		}
	}

	public static final List<Map<String, Object>> readObjectList(InputStream in) {
		try {
			return OM.readValue(in, ObjectList);
		} catch (IOException e) {
			throw new WrappedException(e);
		}
	}

	public static final <T> T readValue(InputStream in, Class<T> type) {
		try {
			return OM.readValue(in, type);
		} catch (IOException e) {
			throw new WrappedException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static final <T> T readGeneric(InputStream in, Class<?> parametrized, Class<?>... parameterClasses) {
		try {
			if (ArrayKit.isNotEmpty(parameterClasses)) {
				int last = parameterClasses.length - 1;
				JavaType[] types = new JavaType[] { TF.constructType(parameterClasses[last]) };
				for (int i = last - 1; i >= 0; i--) {
					types[0] = TF.constructSimpleType(parameterClasses[i], types);
				}
				return OM.readValue(in, TF.constructSimpleType(parametrized, types));
			} else {
				return (T) OM.readValue(in, parametrized);
			}
		} catch (IOException e) {
			throw new WrappedException(e);
		}
	}

	public static final String writeAsString(Object value) {
		try {
			return OM.writeValueAsString(value);
		} catch (IOException e) {
			throw new WrappedException(e);
		}
	}

	public static final byte[] writeAsBytes(Object value) {
		try {
			return OM.writeValueAsBytes(value);
		} catch (IOException e) {
			throw new WrappedException(e);
		}
	}

	public static final void writeValue(Writer out, Object value) {
		try {
			OM.writeValue(out, value);
		} catch (IOException e) {
			throw new WrappedException(e);
		}
	}

	public static final void writeValue(OutputStream out, Object value) {
		try {
			OM.writeValue(out, value);
		} catch (IOException e) {
			throw new WrappedException(e);
		}
	}

}
