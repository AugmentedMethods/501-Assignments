#include "FFT.h"
#include <iostream>
#include <cstring>
#include <stdio.h>
#include <math.h>
#include <fstream>
#include <stdlib.h>
#include <malloc.h>
#include "FFT.h"
#include "ReadAndWrite.h"

using namespace std;

int theSize;

#define PI         3.141592653589793
#define TWO_PI     (2.0 * PI)
#define SWAP(a,b)  tempr=(a);(a)=(b);(b)=tempr

void FFT:: fbConv(char *inFileName, char *IRFileName, char *outFileName)
{

	ReadAndWrite readObj;
	printf("overlap-add FFT convolution\n");
	//Input file signal
	float *inFileSignal=0;
	int inFileSignalSize;

	//IR file signal
	float *IRFileSignal=0;
	int IRFileSignalSize;

	inFileSignal = readObj.readData(inFileName, inFileSignal, &theSize );
	inFileSignalSize = theSize;
	IRFileSignal = readObj.readData(IRFileName, IRFileSignal, &theSize);
	IRFileSignalSize = theSize;

	int outFileSignalSize = inFileSignalSize + IRFileSignalSize - 1;
	float *outFileSignal = new float[outFileSignalSize];

	overlapAdd(inFileSignal, inFileSignalSize, IRFileSignal, IRFileSignalSize, outFileSignal, outFileSignalSize);
	readObj.writeWaveFile(outFileName, outFileSignalSize,outFileSignal);
	printf("out written to file: %s\n", outFileName);
}

void FFT::  overlapAdd(float *inFileSignal,int inFileSignalSize,float * IRFileSignal,int IRFileSignalSize, float *outFileSignal, int outFileSignalSize)
{
        int totalSize = 0;
        int paddedTotalSize = 1;
        totalSize = inFileSignalSize + IRFileSignalSize - 1;

        int i = 0;
        while (paddedTotalSize < totalSize)
        {
                paddedTotalSize <<= 1;
                i++;
        }
        printf("Padded Size: %i exp: %i\n", paddedTotalSize, i);
        printf("Input size: %i\n",inFileSignalSize );
        printf("IR size: %i\n", IRFileSignalSize);
        printf("Sum IR&Input size: %i\n\n", totalSize);

        float *complexResult = new float[2*paddedTotalSize];
        float *input = new float[2*paddedTotalSize];
        float *ir = new float[2*paddedTotalSize];

        padArray(input,inFileSignal, inFileSignalSize,2*paddedTotalSize);
        padArray(ir,IRFileSignal, IRFileSignalSize, 2*paddedTotalSize);
        padZeroes(complexResult, 2*paddedTotalSize);
        fft(input-1, paddedTotalSize, 1);
        fft(ir-1, paddedTotalSize, 1);
        printMinMax(input, paddedTotalSize);
        printMinMax(ir, paddedTotalSize);
        printf("Complex calc\n");
        complexCalculation(input, ir, complexResult, paddedTotalSize);
        printMinMax(complexResult, paddedTotalSize);
        printf("Inverse FFT\n");
        fft(complexResult-1, paddedTotalSize, -1);
        printf("Scaling\n");
        scaleFFT(complexResult, paddedTotalSize);
        unpadArray(complexResult, outFileSignal, outFileSignalSize);
        scale(outFileSignal, outFileSignalSize);
}

void FFT:: printMinMax(float test[],int size)
{
        int i;
        float min = 0, max = 0;
        for(i = 0; i < size; i++)
        {
                if(min > test[i])
                        min = test[i];
                if(max < test[i])
                        max = test[i];
        }
        printf("Min: %f\n", min);
        printf("Max: %f\n", max);
        printf("Size: %i\n", i);
}

void FFT::  complexCalculation(float complexInput[],float complexIR[],float complexResult[], int size)
{
        int i = 0;
        int tempI = 0;
        for(i = 0; i < size; i++) {
                tempI = i * 2;
            complexResult[tempI] = complexInput[tempI] * complexIR[tempI] - complexInput[tempI+1] * complexIR[tempI+1];
            complexResult[tempI+1] = complexInput[tempI+1] * complexIR[tempI] + complexInput[tempI] * complexIR[tempI+1];
        }
}

void FFT:: padZeroes(float toPad[], int size)
{
        memset(toPad, 0, size);
}

void FFT:: unpadArray(float result[], float complete[], int size)
{
        int i, j;

          for(i = 0, j = 0; i < size; i++, j+=2)
          {
            complete[i] = result[j];
          }
}

void FFT:: padArray(float output[],float data[], int dataLen, int size)
{
        int i, k;
        for(i = 0, k = 0; i < dataLen; i++, k+=2)
        {
            output[k] = data[i];
            output[k + 1] = 0;
        }
        i = k;
        memset(output + k, 0, size -1);
}



void FFT:: scaleFFT(float result[], int size)
{
        float val;
        int i = 0;
        for(i = 0; i < size; i++) {
            result[i*2] /= (float)size;
            result[(i*2)+1] /= (float)size;
         }
}

void FFT:: fft(float data[], int nn, int isign)
{
    unsigned long n, mmax, m, j, istep, i;
    float wtemp, wr, wpr, wpi, wi, theta;
    float tempr, tempi;

    n = nn << 1;
    j = 1;

    for (i = 1; i < n; i += 2)
    {
            if (j > i)
            {
                    SWAP(data[j], data[i]);
                    SWAP(data[j+1], data[i+1]);
            }
            m = nn;
            while (m >= 2 && j > m)
            {
                    j -= m;
                    m >>= 1;
            }
            j += m;
    }

    mmax = 2;
    while (n > mmax)
    {
            istep = mmax << 1;
            theta = isign * (6.28318530717959 / mmax);
            wtemp = sin(0.5 * theta);
            wpr = -2.0 * wtemp * wtemp;
            wpi = sin(theta);
            wr = 1.0;
            wi = 0.0;
            for (m = 1; m < mmax; m += 2)
            {
                    for (i = m; i <= n; i += istep)
                    {
                            j = i + mmax;
                            tempr = wr * data[j] - wi * data[j+1];
                            tempi = wr * data[j+1] + wi * data[j];
                            data[j] = data[i] - tempr;
                            data[j+1] = data[i+1] - tempi;
                            data[i] += tempr;
                            data[i+1] += tempi;
                    }
                    wr = (wtemp = wr) * wpr - wi * wpi + wr;
                    wi = wi * wpr + wtemp * wpi + wi;
            }
            mmax = istep;
    }
}

void FFT:: scale(float signal[], int samples)
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

FFT::FFT(void)
{
}


FFT::~FFT(void)
{
}
