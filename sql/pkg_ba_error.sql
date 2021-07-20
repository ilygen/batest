CREATE OR REPLACE PACKAGE BA.PKG_BA_ERROR authid definer IS

  --bc預定exception;
  --預定exception發生時，Raise_application_error(-20000, 'E0001');
  --其中錯誤訊息帶入errorcode
  e_bc_exception EXCEPTION;
  PRAGMA EXCEPTION_INIT(e_bc_exception, -20000);

  TYPE error_record IS RECORD(
  program_owner all_objects.owner%TYPE,
  program_name  all_objects.object_name%TYPE,
  line_number   PLS_INTEGER);
  --
  -- Parse a line with this format:
  -- ORA-NNNNN: at "SCHEMA.PROGRAM", line NNN
  --
  FUNCTION info(backtrace_in IN VARCHAR2) RETURN error_record;

  FUNCTION show_info(backtrace_in IN VARCHAR2) RETURN VARCHAR2;

  /* Return msg for debug
  * The msg format is like [ORA-01403: no data found] occurs [ORA-06512: at "SCOTT.PROC1", line 4]
  */
  FUNCTION show_debug_msg RETURN VARCHAR2;

END PKG_BA_ERROR;
/

CREATE OR REPLACE PACKAGE BODY BA.PKG_BA_ERROR IS
  -- Strings that delimit different parts of line in stack.
  --ORA-NNNNN: at "SCHEMA.PROGRAM", line NNN
  c_name_delim CONSTANT CHAR(1) := '"';
  c_dot_delim  CONSTANT CHAR(1) := '.';
  c_line_delim CONSTANT CHAR(4) := 'line';
  c_eol_delim  CONSTANT CHAR(1) := CHR(10);

  ----backtrace_in : DBMS_UTILITY.format_error_backtrace
  FUNCTION info(backtrace_in IN VARCHAR2) RETURN error_record IS
	-- Lots of INSTRs to come; these variables keep track
	-- of the start and end points of various portions of the string.
	--l_at_loc         PLS_INTEGER;
	l_dot_loc        PLS_INTEGER;
	l_name_start_loc PLS_INTEGER;
	l_name_end_loc   PLS_INTEGER;
	l_line_loc       PLS_INTEGER;
	l_eol_loc        PLS_INTEGER;
	--
	retval error_record;

  PROCEDURE initialize_values IS
  BEGIN
    l_name_start_loc := INSTR(backtrace_in, c_name_delim, 1, 1);
    l_dot_loc        := INSTR(backtrace_in, c_dot_delim);
    l_name_end_loc   := INSTR(backtrace_in, c_name_delim, 1, 2);
    l_line_loc       := INSTR(backtrace_in, c_line_delim);
    l_eol_loc        := INSTR(backtrace_in, c_eol_delim);
  END initialize_values;
  BEGIN
  initialize_values;
  --
  retval.program_owner := SUBSTR(backtrace_in,
                   l_name_start_loc + 1,
                   l_dot_loc - l_name_start_loc - 1);
  --
  retval.program_name := SUBSTR(backtrace_in,
                  l_dot_loc + 1,
                  l_name_end_loc - l_dot_loc - 1);
  --
  retval.line_number := SUBSTR(backtrace_in,
                 l_line_loc + 5,
                 l_eol_loc - l_line_loc - 5);
  RETURN retval;
  END info;

  ----backtrace_in : DBMS_UTILITY.format_error_backtrace
  PROCEDURE output_info(backtrace_in IN VARCHAR2) IS
  l_line error_record;
  BEGIN
  l_line := info(backtrace_in);
  DBMS_OUTPUT.put_line('Program owner = ' || l_line.program_owner);
  DBMS_OUTPUT.put_line('Program name = ' || l_line.program_name);
  DBMS_OUTPUT.put_line('Line number = ' || l_line.line_number);
  END output_info;

  FUNCTION show_info(backtrace_in IN VARCHAR2) RETURN VARCHAR2 IS
  l_line error_record;
  v_msg  VARCHAR2(255);
  BEGIN
  l_line := info(backtrace_in);
  v_msg  := 'Program owner = ' || l_line.program_owner ||
        ', Program name = ' || l_line.program_name ||
        ', Line number = ' || l_line.line_number;

  RETURN v_msg;
  END show_info;

  /* Return msg for debug
  *  The msg format is like [ORA-01403: no data found] occurs [ORA-06512: at "SCOTT.PROC1", line 4]
  */
  FUNCTION show_debug_msg(backtrace_in IN VARCHAR2) RETURN VARCHAR2 IS
  v_msg       VARCHAR2(255);
  v_start_pos INTEGER := 1;
  v_end_pos   INTEGER;
  BEGIN
  v_end_pos := Instr(backtrace_in, Chr(10), v_start_pos);
  --DBMS_OUTPUT.PUT_LINE('v_end_pos : ' || v_end_pos);
  IF v_end_pos > 0
  THEN
    v_msg := '[' || SQLERRM || '] occured [' ||
         Substr(backtrace_in, v_start_pos, (v_end_pos - v_start_pos)) || ']';
    --DBMS_OUTPUT.PUT_LINE(v_msg);
  END IF;
  RETURN v_msg;
  END show_debug_msg;

  /* Return message for debug
  *  The message format is like "[ORA-01403: no data found] occurs [ORA-06512: at "SCOTT.PROC1", line 4]"
  */
  FUNCTION show_debug_msg RETURN VARCHAR2 IS
  BEGIN
  --DBMS_OUTPUT.PUT_LINE(DBMS_UTILITY.format_error_backtrace);
  RETURN show_debug_msg(DBMS_UTILITY.format_error_backtrace);
  END show_debug_msg;


END PKG_BA_ERROR;
/

