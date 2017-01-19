package core.cfg.declaration;

/**
 * Loại node
 * phát sinh thêm loại node nào mới thì cho vào đây
 */
public enum NodeType {
	/**
	 *	node bình thường: phép gán...
	 */
	NORMAL, 
	/**
	 *	if-else
	 */
	IF, 
	/**
	 *	switch case
	 */
	SWITCH,
	/**
	 *	for loop
	 */
	FOR,
	/**
	 *	while loop
	 */
	WHILE,
	/**
	 *	do-while loop
	 */
	DOWHILE,
	/**
	 *	break statement
	 */
	BREAK,
	/**
	 *	continue statement
	 */
	CONTINUE,
	/**
	 *	return statement
	 */
	RETURN,
	/**
	 *	beginning of function
	 */
	BEGIN,
	/**
	 *	end of function
	 */
	END
}
