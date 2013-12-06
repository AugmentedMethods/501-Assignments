#pragma once

#include <iostream>
#include <fstream>
#include <strstream>
#include <istream>
#include <vector>
#include <iterator>     // std::istream_iterator

#include <stdio.h>
#include <stdlib.h>
#include <math.h>

class BasicConvolution
{
public:
	BasicConvolution(void);
	~BasicConvolution(void);

	/*
	For the file creation
	*/

	/*  CONSTANTS  ***************************************************************/
	#define PI					3.14159265358979

	/*  Test tone frequency in Hz  */
	#define FREQUENCY			440.0

	/*  Test tone duration in seconds  */
	#define DURATION			2.0				

	/*  Standard sample rate in Hz  */
	#define SAMPLE_RATE			44100.0

	/*  Standard sample size in bits  */
	#define BITS_PER_SAMPLE		16

	/*  Standard sample size in bytes  */		
	#define BYTES_PER_SAMPLE	(BITS_PER_SAMPLE/8)

	/*  Number of channels  */
	#define MONOPHONIC			1
	#define STEREOPHONIC		2

private:
	void readFile(char *buffer, std::ifstream *file, size_t &dateSetSize );
	void convolute();
	void formatData(char *array, short int *newArray, size_t dataSetSize);
	short int endianConverter(short int  val);
	size_t fwriteIntLSB(int data, FILE *stream);
	size_t fwriteShortLSB(short int data, FILE *stream);
	void writeWaveFileHeader(int channels, int numberSamples,
						 double outputRate, FILE *outputFile);

	short int *convolutionArray; 
	short int *inputData;
	short int *impulseData;
	char *tempBuffer;

	int impulseCounter;
	int inputCounter;
	size_t inputDataSize;
	size_t impulseDataSize;

	int bufferSize;


	std::ifstream *input;
	std::ifstream *impulse;
};

