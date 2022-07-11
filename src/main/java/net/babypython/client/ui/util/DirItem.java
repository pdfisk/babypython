/**
 * MIT License
 *
 * Copyright (c) 2022 Peter Fisk
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.babypython.client.ui.util;

import net.babypython.client.vm.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class DirItem implements Comparable<DirItem> {
	private static final String dirListKey = "D";
	private static final String fileListKey = "F";
	private static final String nameKey = "N";
	private ArrayList<DirItem> directories;
	private ArrayList<String> files;
	private String name;
	private DirItem parent;
	private boolean updateFlag;

	public DirItem(DirItem parent, String name) {
		this.directories = new ArrayList<DirItem>();
		this.files = new ArrayList<String>();
		this.name = name;
		this.parent = parent;
		updateFlag = false;
	}

	public DirItem(String name) {
		this(null, name);
	}

	public void addFile(String fileName) {
		if (!this.files.contains(fileName)) {
			this.files.add(fileName);
			setRootUpdateFlag();
		}
	}

	public void addSubDirectory(String dirName) {
		if (!hasSubDirectory(dirName)) {
			this.directories.add(new DirItem(this, dirName));
			setRootUpdateFlag();
		}
	}

	@Override
	public int compareTo(DirItem dir) {
		return this.name.compareTo(dir.getName());
	}

	@SuppressWarnings("unchecked")
	public void fromJSON(String jsonStr) {
//		Object obj = JSON.decode(jsonStr);
//		if (obj instanceof HashMap)
//			fromMap(this, (HashMap<String, Object>) obj);
	}

	@SuppressWarnings("unchecked")
	private void fromMap(DirItem dirItem, HashMap<String, Object> map) {
		if (map.size() == 0)
			return;
		String name;
		ArrayList<Object> dirList = null;
		ArrayList<Object> fileList = null;
		if (map.containsKey(nameKey))
			name = map.get(nameKey).toString();
		else
			name = "?";
		if (map.containsKey(dirListKey)) {
			Object dirListObj = map.get(dirListKey);
			if (dirListObj instanceof ArrayList)
				dirList = (ArrayList<Object>) dirListObj;
		}
		if (dirList == null)
			dirList = new ArrayList<Object>();
		if (map.containsKey(fileListKey)) {
			Object fileListObj = map.get(fileListKey);
			if (fileListObj instanceof ArrayList)
				fileList = (ArrayList<Object>) fileListObj;
		}
		if (fileList == null)
			fileList = new ArrayList<Object>();
		dirItem.name = name;
		dirItem.directories.clear();
		for (Object obj : dirList) {
			if (obj instanceof HashMap)
				dirItem.directories.add(dirItem
						.fromMap((HashMap<String, Object>) obj));
		}
		dirItem.files.clear();
		for (Object obj : fileList)
			dirItem.files.add(obj.toString());
	}

	private DirItem fromMap(HashMap<String, Object> map) {
		DirItem dirItem = new DirItem(this, "temp");
		fromMap(dirItem, map);
		return dirItem;
	}

	public int getDepth() {
		if (this.parent == null)
			return 0;
		else
			return this.parent.getDepth() + 1;
	}

	public ArrayList<DirItem> getDirectoryList() {
		ArrayList<DirItem> list = new ArrayList<DirItem>();
		getDirectoryList(list);
		return list;
	}

	public void getDirectoryList(ArrayList<DirItem> list) {
		list.add(this);
		if (this.directories.size() > 0) {
			Collections.sort(this.directories);
			for (DirItem dir : this.directories)
				dir.getDirectoryList(list);
		}
	}

	public ArrayList<String> getFiles() {
		Collections.sort(this.files);
		return this.files;
	}

	public String getIndentedName() {
		String pad = "";
		for (int i = 0; i < getDepth(); i++)
			pad += StringUtil.LIST_INDENT_PAD;
		return pad + getName();
	}

	public String getName() {
		return this.name;
	}

	public DirItem getParent() {
		return this.parent;
	}

	public String getPath() {
		String pathName = this.name;
		if (pathName.startsWith("["))
			pathName = pathName.substring(1, pathName.length() - 1);
		if (this.parent != null)
			return this.parent.getPath() + "/" + pathName;
		else
			return pathName;
	}

	public DirItem getRoot() {
		if (this.parent == null)
			return this;
		else
			return this.parent.getRoot();
	}

	public String getUndecoratedName() {
		return StringUtil.stripLeading(
				StringUtil.stripTrailing(this.name, "]"), "[");
	}

	public boolean getUpdateFlag() {
		return this.updateFlag;
	}

	public boolean hasSubDirectory(String name) {
		for (DirItem dir : this.directories)
			if (name.equals(dir.getName()))
				return true;
		return false;
	}

	public void removeFile(String fileName) {
		if (this.files.contains(fileName)) {
			this.files.remove(fileName);
			setRootUpdateFlag();
		}
	}

	public void removeSubDirectory(String dirName) {
		for (DirItem dir : this.directories)
			if (dirName.equals(dir.getName())) {
				this.directories.remove(dir);
				setRootUpdateFlag();
				break;
			}
	}

	public void resetUpdateFlag() {
		this.updateFlag = false;
	}

	private void setRootUpdateFlag() {
		getRoot().setUpdateFlag();
	}

	public void setUpdateFlag() {
		this.updateFlag = true;
	}

	private HashMap<String, Object> toHashMap() {
		HashMap<String, Object> dirMap = new HashMap<String, Object>();
		ArrayList<Object> dirList = new ArrayList<Object>();
		ArrayList<String> fileList = new ArrayList<String>();
		for (DirItem dirItem : this.directories)
			dirList.add(dirItem.toHashMap());
		for (String fileName : this.files)
			fileList.add(fileName);
		dirMap.put(dirListKey, dirList);
		dirMap.put(fileListKey, fileList);
		dirMap.put(nameKey, getName());
		return dirMap;
	}

	public String toJSON() {
		return "";
//		return JSON.encode(toHashMap());
	}

}
