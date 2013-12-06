#include "stdafx.h"
#include "IS.h"

#include <iostream>
#include <stdio.h>
#include <math.h>
#include <fstream>
#include <stdlib.h>
#include <malloc.h>

#include "ConvolveIS.h"

using namespace std;

int size;


IS::IS(void)
{
}


IS::~IS(void)
{
}

void IS:: tbConv(char *inFileName, char *IRFileName, char *outFileName)
{
        printf("Input-side convolution....\n");
        //Input file signal
        float *inFileSignal;
        int inFileSignalSize;

        //IR file signal
        float *IRFileSignal;
        int IRFileSignalSize;

        inFileSignal = readData(inFileName, inFileSignal, &size );
        inFileSignalSize = size;

        IRFileSignal = readData(IRFileName, IRFileSignal, &size);
        IRFileSignalSize = size;
        int outFileSignalSize = inFileSignalSize + IRFileSignalSize - 1;
        float *outFileSignal = new float[outFileSignalSize];

        convolve(inFileSignal, inFileSignalSize, IRFileSignal, IRFileSignalSize, outFileSignal, outFileSignalSize);
        scale(outFileSignal, outFileSignalSize);
        writeWaveFile(outFileName, outFileSignalSize, outFileSignal);
        printf("out written to file: %s\n", outFileName);
}

void IS:: scale(float signal[], int samples)
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

        printf("min: %f\n", min);
        printf("max: %f\n", max);

        for(i = 0; i < samples; i++)
        {
                signal[i] = signal[i] / max;
        }
}

void IS:: convolve(float x[], int N, float h[], int M, float y[], int P) {
        int n, m;

        for (n = 0; n < P; n++)
                y[n] = 0.0;

        float percent = 0.0;
        for (n = 0; n < N; n++) {
                if(n % 10000 == 0)
                {
                        percent = ((float)n / (float)N) * 100;
                        printf("%i of %i %.2f%%\n", n, N, percent);
                }
                for (m = 0; m < M; m++)
                {
                        y[n+m] += x[n] * h[m];
                }
        }

}
