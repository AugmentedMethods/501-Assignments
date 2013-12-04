#pragma once

#include <iostream>
#include <fstream>
#include <strstream>
#include <istream>
#include <vector>
#include <iterator>     // std::istream_iterator

class BasicConvolution
{
public:
	BasicConvolution(void);
	~BasicConvolution(void);

private:
	void readInputFile();
	void readImpulseFile();
	void convolute();

	char *convolutionArray; 
	char *inputData;
	char *impulseData;

	int impulseCounter;
	int inputCounter;

	int bufferSize;

	std::streampos fileSizeInput;
	std::streampos fileSizeImpulse;

	std::ifstream input;
	std::ifstream impulse;
};

