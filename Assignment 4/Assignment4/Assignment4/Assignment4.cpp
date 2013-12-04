// Assignment4.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include "BasicConvolution.h"
#include <cstdio>
#include <ctime>


int _tmain(int argc, _TCHAR* argv[])
{

	std::clock_t start;
    double duration;

    start = std::clock();

	BasicConvolution *testObj = new BasicConvolution();	
	duration = ( std::clock() - start ) / (double) CLOCKS_PER_SEC;
	std::cout<<"Duration: "<< duration <<'\n';
	//delete testObj;
	return 0;
}

