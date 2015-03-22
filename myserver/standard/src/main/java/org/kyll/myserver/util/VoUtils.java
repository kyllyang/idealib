package org.kyll.myserver.util;

import org.apache.commons.beanutils.BeanUtils;
import org.kyll.myserver.base.paginated.Dataset;
import org.kyll.myserver.business.sysmanager.entity.Attachment;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * User: Kyll
 * Date: 2014-11-07 13:19
 */
public class VoUtils {
	public static <E, V> V convert(E e, Class<V> clazz) {
		V v = null;
		if (e != null) {
			try {
				v = clazz.newInstance();
				BeanUtils.copyProperties(v, e);
			} catch (InstantiationException | IllegalAccessException | InvocationTargetException ex) {
				ex.printStackTrace();
			}
		}
		return v;
	}

	public static <E, V> List<V> convert(List<E> tList, Class<V> clazz) {
		List<V> vList = null;
		if (tList != null) {
			vList = new ArrayList<>();
			for (E e : tList) {
				vList.add(VoUtils.convert(e, clazz));
			}
		}
		return vList;
	}

	public static <E, V> Dataset<V> convert(Dataset<E> tDataset, Class<V> clazz) {
		Dataset<V> vDataset = null;
		if (tDataset != null) {
			vDataset = new Dataset<>();
			vDataset.setPaginated(tDataset.getPaginated());
			vDataset.setDataList(VoUtils.convert(tDataset.getDataList(), clazz));
		}
		return vDataset;
	}

	public static <E, V> V convert(E e, Class<V> clazz, VoHandler<E, V> voHandler) {
		V v = null;
		if (e != null) {
			try {
				v = clazz.newInstance();
				BeanUtils.copyProperties(v, e);
				voHandler.handler(e, v);
			} catch (InstantiationException | IllegalAccessException | InvocationTargetException ex) {
				ex.printStackTrace();
			}
		}
		return v;
	}

	public static <E, V> List<V> convert(List<E> tList, Class<V> clazz, VoHandler<E, V> voHandler) {
		List<V> vList = null;
		if (tList != null) {
			vList = new ArrayList<>();
			for (E e : tList) {
				vList.add(VoUtils.convert(e, clazz, voHandler));
			}
		}
		return vList;
	}

	public static <E, V> Dataset<V> convert(Dataset<E> tDataset, Class<V> clazz, VoHandler<E, V> voHandler) {
		Dataset<V> vDataset = null;
		if (tDataset != null) {
			vDataset = new Dataset<>();
			vDataset.setPaginated(tDataset.getPaginated());
			vDataset.setDataList(VoUtils.convert(tDataset.getDataList(), clazz, voHandler));
		}
		return vDataset;
	}

	public static List<CommonsMultipartFile> getCommonsMultipartFileList(Object object) {
		List<CommonsMultipartFile> commonsMultipartFileList = new ArrayList<>();

		Class clazz = object.getClass();
		Method[] methods = clazz.getMethods();
		try {
			for (Method method : methods) {
				Class returnType = method.getReturnType();
				if (CommonsMultipartFile.class == returnType) {
					Object returnValue = method.invoke(object);
					if (returnValue != null) {
						commonsMultipartFileList.add((CommonsMultipartFile) returnValue);
					}
				} else if (CommonsMultipartFile[].class == returnType) {
					Object returnValue = method.invoke(object);
					if (returnValue != null) {
						CommonsMultipartFile[] commonsMultipartFiles = (CommonsMultipartFile[]) returnValue;
						for (CommonsMultipartFile commonsMultipartFile : commonsMultipartFiles) {
							if (commonsMultipartFile.getSize() > 0) {
								commonsMultipartFileList.add(commonsMultipartFile);
							}
						}
					}
				}
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}

		return commonsMultipartFileList;
	}

	public static Attachment convert(CommonsMultipartFile commonsMultipartFile) {
		Attachment attachment = new Attachment();
	//	attachment.setEntityName();
	//	attachment.setEntityId();
		attachment.setContentType(commonsMultipartFile.getContentType());
		attachment.setFileSize(commonsMultipartFile.getSize());

		String originalFilename = commonsMultipartFile.getOriginalFilename();
		attachment.setOriginalFilename(originalFilename);
		attachment.setRandomFilename(originalFilename.substring(0, originalFilename.lastIndexOf('.')) + "_" + UUID.randomUUID().toString().replace("-", ""));
		attachment.setExtensionName(originalFilename.substring(originalFilename.lastIndexOf('.') + 1));

		return attachment;
	}

	public static interface VoHandler<T, V> {
		void handler(T t, V v);
	}

	private VoUtils() {
	}
}
