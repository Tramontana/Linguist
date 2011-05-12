// ValidationListener.java

package net.eclecity.linguist.component;

/******************************************************************************
	ValidationListener.
	A listener for VFormItem components.

	@author Graham Trott of TechModern Ltd.
	@version Java 1.2

	Class Version -><pre>
	[5.0 GT]   04/11/00  Created.
	</pre>
*/
public interface ValidationListener
{
	public void validationFailed(VFormItem item);
}
