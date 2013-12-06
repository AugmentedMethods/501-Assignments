#include "BasicConvolution.h"
#include <iostream>
#include <stdio.h>
#include <math.h>
#include <fstream>
#include <stdlib.h>
#include <cstdio>
#include <ctime>

#include "ReadAndWrite.h"
using namespace std;

int size;

void BasicConvolution::convolute(char *inFileName, char *IRFileName, char *outFileName)
{
        printf("Basic Convolution\n");

        float *inputSignals =0;
        int inFileSignalSize;

        //IR file signal
        float *IRFileSignal =0;
        int IRFileSignalSize;

		ReadAndWrite readObj;

        inputSignals = readObj.readData(inFileName, inputSignals, &size );
        inFileSignalSize = size;

        IRFileSignal = readObj.readData(IRFileName, IRFileSignal, &size);
        IRFileSignalSize = size;
        int outFileSignalSize = inFileSignalSize + IRFileSignalSize - 1;
        float *outFileSignal = new float[outFileSignalSize];

		std::clock_t start;
		double duration;

		start = std::clock();
	
        convolve(inputSignals, inFileSignalSize, IRFileSignal, IRFileSignalSize, outFileSignal, outFileSignalSize);
        scale(outFileSignal, outFileSignalSize);
        readObj.writeWaveFile(outFileName, outFileSignalSize, outFileSignal);

        duration = ( std::clock() - start ) / (double) CLOCKS_PER_SEC;
		std::cout<<"Duration: "<< duration <<'\n';

}

void BasicConvolution::scale(float signal[], int samples)
{
        float min = 0, max = 0;
        int i = 0;

        for(i = 0; i < samples; i++)
        {
            if(signal[i] > max)
                    max = signal[i];
            if(signal[i] < min)
                    min = signal[i];
        }

        min = min * -1;
        if(min > max)
			max = min;

        for(i = 0; i < samples; i++)
        {
            signal[i] = signal[i] / max;
        }
}

void BasicConvolution::convolve(float x[], int N, float h[], int M, float y[], int P) {
        int n, m;


        float percent = 0.0;
        for (n = 0; n < N; n++) {
            for (m = 0; m < M; m++)
            {
                y[n+m] += x[n] * h[m];
            }
        }

}
BasicConvolution::BasicConvolution(void)
{
}


BasicConvolution::~BasicConvolution(void)
{
}
