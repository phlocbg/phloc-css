/**
 * Copyright (C) 2006-2012 phloc systems
 * http://www.phloc.com
 * office[at]phloc[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.phloc.css.decl;

import com.phloc.commons.annotations.MustImplementEqualsAndHashcode;
import com.phloc.css.ICSSWriteable;

/**
 * Marker interface for all top level CSS elements that can occur in any order
 * <ul>
 * <li>style rules</li>
 * <li>page rules</li>
 * <li>media rules</li>
 * <li>font face rules</li>
 * <li>keyframes rules</li>
 * <li>viewport rules</li>
 * </ul>
 * 
 * @author philip
 */
@MustImplementEqualsAndHashcode
public interface ICSSTopLevelRule extends ICSSWriteable
{
  /* empty */
}