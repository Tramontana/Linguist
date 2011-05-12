!	Benchmark.ls

	package basic
	
	variable N
	variable M
	variable Array 100000
	hashtable Data
	variable Start
	variable Finish
	
	prompt "FOR loop counting to 1,000,000"
	put the millisecond into Start
	put 0 into N
	while N is less than 1000000 increment N
	put the millisecond into Finish
	take Start from Finish giving N
	prompt N cat " milliseconds"
	
	prompt "Compare 1,000,000 long integers for equality"
	put the millisecond into Start
	put 0 into N
	while N is less than 1000000
	begin
		if N is 1234567890 begin end
		increment N
	end
	put the millisecond into Finish
	take Start from Finish giving N
	prompt N cat " milliseconds"

	prompt "Allocate and initialize a 100,000 element array"
	put the millisecond into Start
	put 0 into N
	while N is less than 100000
	begin
		index Array to N
		put N into Array
		increment N
	end
	put the millisecond into Finish
	take Start from Finish giving N
	prompt N cat " milliseconds"

	prompt "Allocate and initialize a 500x500 hashtable"
	put the millisecond into Start
	put 0 into N
	while N is less than 500
	begin
		put 0 into M
		while M is less than 500
		begin
			put M into Data as N cat " " cat M
			increment M
		end
		increment N
	end
	put the millisecond into Finish
	take Start from Finish giving N
	prompt N cat " milliseconds"

	exit
