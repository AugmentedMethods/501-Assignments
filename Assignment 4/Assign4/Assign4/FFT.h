#pragma once
class FFT
{
public:
	FFT(void);
	~FFT(void);

	void fbConv(char *inputFileName, char *IRFileName, char *outputFileName);
	void overlapAdd(float *inputFileSignal,int inputFileSignalSize,float * IRFileSignal,int IRFileSignalSize, char *outputFileName);
	void fft(float data[], int nn, int isign);
	void complexCalculation(float complexInput[],float complexIR[],float complexResult[], int paddedSize);
	void padArray(float output[],float data[], int dataLen, int size);
	void unpadArray(float result[], float complete[], int size);
	void overlapAdd(float *inputFileSignal,int inputFileSignalSize,float * IRFileSignal,int IRFileSignalSize, float *outputFileSignal, int outputFileSignalSize);
	void scaleFFT(float result[], int size);
	void printMinMax(float test[], int size);
	void padZeroes(float toPad[], int size);
	void scale(float signal[], int samples);
};

