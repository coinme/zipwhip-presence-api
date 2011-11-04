package com.zipwhip.signals.util;

import java.util.Map;

import com.zipwhip.signals.message.MessageSerializer;

/**
 * Created by IntelliJ IDEA.
 * User: Michael
 * Date: Dec 13, 2010
 * Time: 2:14:37 AM
 * 
 * For serializing into a map
 */
public interface SignalsSerializer<T> {

	public Map<String, Object> serialize(T item);

	public Map<String, Object> serialize(MessageSerializer serializer, T item);
}
