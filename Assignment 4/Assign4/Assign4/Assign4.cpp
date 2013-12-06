// Assign4.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include "BasicConvolution.h"
#include "FFT.h"


int _tmain(int argc, _TCHAR* argv[])
{
	BasicConvolution test;
	FFT test2;

	test.convolute("DrumsDry.wav", "l960large_brite_hall.wav", "Out.wav");
	//test2.fbConv("DrumsDry.wav", "l960large_brite_hall.wav", "Out.wav");
	return 0;
}

